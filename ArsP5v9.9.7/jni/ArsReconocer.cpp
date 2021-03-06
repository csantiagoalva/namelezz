#include <jni.h>
#include <android/log.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>

#ifdef USE_OPENGL_ES_1_1
#include <GLES/gl.h>
#include <GLES/glext.h>
#else
#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>
#endif

#include <QCAR/QCAR.h>
#include <QCAR/CameraDevice.h>
#include <QCAR/Renderer.h>
#include <QCAR/VideoBackgroundConfig.h>
#include <QCAR/Trackable.h>
#include <QCAR/Tool.h>
#include <QCAR/Tracker.h>
#include <QCAR/TrackerManager.h>
#include <QCAR/ImageTracker.h>
#include <QCAR/CameraCalibration.h>
#include <QCAR/UpdateCallback.h>
#include <QCAR/DataSet.h>

#include "SampleUtils.h"
//#include "Texture.h"
//#include "CubeShaders.h"
//#include "Teapot.h"

#ifdef __cplusplus
extern "C"
{
#endif

// Textures:
//int textureCount                = 0;
//Texture** textures              = 0;

// OpenGL ES 2.0 specific:
#ifdef USE_OPENGL_ES_2_0
unsigned int shaderProgramID    = 0;
GLint vertexHandle              = 0;
GLint normalHandle              = 0;
GLint textureCoordHandle        = 0;
GLint mvpMatrixHandle           = 0;
#endif

unsigned int screenWidth2        = 0;
unsigned int screenHeight2       = 0;

bool isActivityInPortraitMode2   = false;

QCAR::Matrix44F projectionMatrix2;

static const float kObjectScale = 3.f;

QCAR::DataSet* dataSetStonesAndChips2    = 0;

bool switchDataSetAsap2          = false;


JNIEXPORT int JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_getOpenGlEsVersionNative(JNIEnv *, jobject)
{
#ifdef USE_OPENGL_ES_1_1
    return 1;
#else
    return 2;
#endif
}


JNIEXPORT void JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_setActivityPortraitMode(JNIEnv *, jobject, jboolean isPortrait)
{
    isActivityInPortraitMode2 = isPortrait;
}



JNIEXPORT void JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_switchDatasetAsap(JNIEnv *, jobject)
{
    switchDataSetAsap2 = true;
}


JNIEXPORT int JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_initTracker(JNIEnv *, jobject)
{
    LOG("Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_initTracker");

    // Initialize the image tracker:
    QCAR::TrackerManager& trackerManager = QCAR::TrackerManager::getInstance();
    QCAR::Tracker* tracker = trackerManager.initTracker(QCAR::Tracker::IMAGE_TRACKER);
    if (tracker == NULL)
    {
        LOG("Failed to initialize ImageTracker.");
        return 0;
    }

    LOG("Successfully initialized ImageTracker.");
    return 1;
}


JNIEXPORT void JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_deinitTracker(JNIEnv *, jobject)
{
    LOG("Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_deinitTracker");

    // Deinit the image tracker:
    QCAR::TrackerManager& trackerManager = QCAR::TrackerManager::getInstance();
    trackerManager.deinitTracker(QCAR::Tracker::IMAGE_TRACKER);
}


JNIEXPORT int JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_loadTrackerData(JNIEnv *env, jobject,jstring fileName)
{
    LOG("Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_loadTrackerData");

    // Get the image tracker:
    QCAR::TrackerManager& trackerManager = QCAR::TrackerManager::getInstance();
    QCAR::ImageTracker* imageTracker = static_cast<QCAR::ImageTracker*>(
                    trackerManager.getTracker(QCAR::Tracker::IMAGE_TRACKER));
    if (imageTracker == NULL)
    {
        LOG("Failed to load tracking data set because the ImageTracker has not"
            " been initialized.");
        return 0;
    }

    dataSetStonesAndChips2 = imageTracker->createDataSet();
    if (dataSetStonesAndChips2 == 0)
    {
        LOG("Failed to create a new tracking data.");
        return 0;
    }

    const char *nativeString = env->GetStringUTFChars(fileName, 0);
    if (!dataSetStonesAndChips2->load(nativeString, QCAR::DataSet::STORAGE_ABSOLUTE))
    {
        LOG("Failed to load data set.");
        return 0;
    }

    env->ReleaseStringUTFChars(fileName, nativeString);

    if (!imageTracker->activateDataSet(dataSetStonesAndChips2))
    {
        LOG("Failed to activate data set.");
        return 0;
    }

    LOG("Successfully loaded and activated data set.");
    return 1;
}


JNIEXPORT int JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_destroyTrackerData(JNIEnv *, jobject)
{
    LOG("Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_destroyTrackerData");

    QCAR::TrackerManager& trackerManager = QCAR::TrackerManager::getInstance();
    QCAR::ImageTracker* imageTracker = static_cast<QCAR::ImageTracker*>(
        trackerManager.getTracker(QCAR::Tracker::IMAGE_TRACKER));
    if (imageTracker == NULL)
    {
        LOG("Failed to destroy the tracking data set because the ImageTracker has not"
            " been initialized.");
        return 0;
    }

    if (dataSetStonesAndChips2 != 0)
    {
        if (imageTracker->getActiveDataSet() == dataSetStonesAndChips2 &&
            !imageTracker->deactivateDataSet(dataSetStonesAndChips2))
        {
            LOG("Failed to destroy the tracking data set StonesAndChips because the data set "
                "could not be deactivated.");
            return 0;
        }

        if (!imageTracker->destroyDataSet(dataSetStonesAndChips2))
        {
            LOG("Failed to destroy the tracking data set StonesAndChips.");
            return 0;
        }

        LOG("Successfully destroyed the data set StonesAndChips.");
        dataSetStonesAndChips2 = 0;
    }

    return 1;
}


JNIEXPORT void JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_onQCARInitializedNative(JNIEnv *, jobject)
{
    // Register the update callback where we handle the data set swap:
//    QCAR::registerCallback(&updateCallback);

    // Comment in to enable tracking of up to 2 targets simultaneously and
    // split the work over multiple frames:
    // QCAR::setHint(QCAR::HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 2);
    // QCAR::setHint(QCAR::HINT_IMAGE_TARGET_MULTI_FRAME_ENABLED, 1);
}


JNIEXPORT void JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ArsRenderer_renderFrame2(JNIEnv *env, jobject obj)
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    QCAR::State state = QCAR::Renderer::getInstance().begin();
    QCAR::Renderer::getInstance().drawVideoBackground();

#ifdef USE_OPENGL_ES_1_1
    // Set GL11 flags:
    glEnableClientState(GL_VERTEX_ARRAY);
    glEnableClientState(GL_NORMAL_ARRAY);
//    glEnableClientState(GL_TEXTURE_COORD_ARRAY);

    glEnable(GL_TEXTURE_2D);
    glEnable(GL_LIGHTING);
//    glDisable(GL_LIGHTING);

#endif

    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);

    // Did we find any trackables this frame?
    for(int tIdx = 0; tIdx < state.getNumActiveTrackables(); tIdx++)
    {
        // Get the trackable:
        const QCAR::Trackable* trackable = state.getActiveTrackable(tIdx);
        QCAR::Matrix44F modelViewMatrix =
            QCAR::Tool::convertPose2GLMatrix(trackable->getPose());

#ifdef USE_OPENGL_ES_1_1
        // Load projection matrix:
        glMatrixMode(GL_PROJECTION);
        glLoadMatrixf(projectionMatrix2.data);

        // Load model view matrix:
        glMatrixMode(GL_MODELVIEW);
        glLoadMatrixf(modelViewMatrix.data);
        glTranslatef(0.f, 0.f, kObjectScale);
        glScalef(kObjectScale, kObjectScale, kObjectScale);

        jclass javaClass = env->GetObjectClass(obj);

        char *buf = (char*)malloc(10);
        strcpy(buf, trackable->getName());
   	    jstring jstrBuf = env->NewStringUTF(buf);

	    jmethodID method1 = env->GetMethodID(javaClass, "prueba2", "(Ljava/lang/String;)V");

	    env->CallVoidMethod(obj, method1,jstrBuf);
	    env->DeleteLocalRef(jstrBuf);

#endif

    }

    glDisable(GL_DEPTH_TEST);

#ifdef USE_OPENGL_ES_1_1
//    glDisable(GL_TEXTURE_2D);
    glDisableClientState(GL_VERTEX_ARRAY);
    glDisableClientState(GL_NORMAL_ARRAY);
#endif

    QCAR::Renderer::getInstance().end();
}


void
configureVideoBackground2()
{
    // Get the default video mode:
    QCAR::CameraDevice& cameraDevice = QCAR::CameraDevice::getInstance();
    QCAR::VideoMode videoMode = cameraDevice.
                                getVideoMode(QCAR::CameraDevice::MODE_DEFAULT);


    // Configure the video background
    QCAR::VideoBackgroundConfig config;
    config.mEnabled = true;
    config.mSynchronous = true;
    config.mPosition.data[0] = 0.0f;
    config.mPosition.data[1] = 0.0f;

    if (isActivityInPortraitMode2)
    {
        //LOG("configureVideoBackground PORTRAIT");
        config.mSize.data[0] = videoMode.mHeight
                                * (screenHeight2 / (float)videoMode.mWidth);
        config.mSize.data[1] = screenHeight2;

        if(config.mSize.data[0] < screenWidth2)
        {
            LOG("Correcting rendering background size to handle missmatch between screen and video aspect ratios.");
            config.mSize.data[0] = screenWidth2;
            config.mSize.data[1] = screenWidth2 *
                              (videoMode.mWidth / (float)videoMode.mHeight);
        }
    }
    else
    {
        //LOG("configureVideoBackground LANDSCAPE");
        config.mSize.data[0] = screenWidth2;
        config.mSize.data[1] = videoMode.mHeight
                            * (screenWidth2 / (float)videoMode.mWidth);

        if(config.mSize.data[1] < screenHeight2)
        {
            LOG("Correcting rendering background size to handle missmatch between screen and video aspect ratios.");
            config.mSize.data[0] = screenHeight2
                                * (videoMode.mWidth / (float)videoMode.mHeight);
            config.mSize.data[1] = screenHeight2;
        }
    }

    LOG("Configure Video Background : Video (%d,%d), Screen (%d,%d), mSize (%d,%d)", videoMode.mWidth, videoMode.mHeight, screenWidth2, screenHeight2, config.mSize.data[0], config.mSize.data[1]);

    // Set the config:
    QCAR::Renderer::getInstance().setVideoBackgroundConfig(config);
}



JNIEXPORT void JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_initApplicationNative(
                            JNIEnv* env, jobject obj, jint width, jint height)
{
    LOG("Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_initApplicationNative");

    // Store screen dimensions
    screenWidth2 = width;
    screenHeight2 = height;

    LOG("Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_initApplicationNative finished");
}


JNIEXPORT void JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_deinitApplicationNative(
                                                        JNIEnv* env, jobject obj)
{
    LOG("Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_deinitApplicationNative");
}


JNIEXPORT void JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_startCamera(JNIEnv *,
                                                                         jobject)
{
    LOG("Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_startCamera");

    // Initialize the camera:
    if (!QCAR::CameraDevice::getInstance().init())
        return;

    // Configure the video background
    configureVideoBackground2();

    // Select the default mode:
    if (!QCAR::CameraDevice::getInstance().selectVideoMode(
                                QCAR::CameraDevice::MODE_DEFAULT))
        return;

    // Start the camera:
    if (!QCAR::CameraDevice::getInstance().start())
        return;

    // Uncomment to enable flash
    //if(QCAR::CameraDevice::getInstance().setFlashTorchMode(true))
    //	LOG("IMAGE TARGETS : enabled torch");

    // Uncomment to enable infinity focus mode, or any other supported focus mode
    // See CameraDevice.h for supported focus modes
    //if(QCAR::CameraDevice::getInstance().setFocusMode(QCAR::CameraDevice::FOCUS_MODE_INFINITY))
    //	LOG("IMAGE TARGETS : enabled infinity focus");

    // Start the tracker:
    QCAR::TrackerManager& trackerManager = QCAR::TrackerManager::getInstance();
    QCAR::Tracker* imageTracker = trackerManager.getTracker(QCAR::Tracker::IMAGE_TRACKER);
    if(imageTracker != 0)
        imageTracker->start();
}


JNIEXPORT void JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_stopCamera(JNIEnv *, jobject)
{
    LOG("Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_stopCamera");

    // Stop the tracker:
    QCAR::TrackerManager& trackerManager = QCAR::TrackerManager::getInstance();
    QCAR::Tracker* imageTracker = trackerManager.getTracker(QCAR::Tracker::IMAGE_TRACKER);
    if(imageTracker != 0)
        imageTracker->stop();

    LOG("Antes de stop");
    QCAR::CameraDevice::getInstance().stop();
    LOG("Despues de stop");
    QCAR::CameraDevice::getInstance().deinit();
    LOG("Despues de deinit");

}


JNIEXPORT void JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_setProjectionMatrix(JNIEnv *, jobject)
{
    LOG("Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_setProjectionMatrix");

    // Cache the projection matrix:
    const QCAR::CameraCalibration& cameraCalibration =
                                QCAR::CameraDevice::getInstance().getCameraCalibration();
    projectionMatrix2 = QCAR::Tool::getProjectionGL(cameraCalibration, 2.0f,
                                            2000.0f);
}


JNIEXPORT jboolean JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_activateFlash(JNIEnv*, jobject, jboolean flash)
{
    return QCAR::CameraDevice::getInstance().setFlashTorchMode((flash==JNI_TRUE)) ? JNI_TRUE : JNI_FALSE;
}


JNIEXPORT jboolean JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_autofocus(JNIEnv*, jobject)
{
    return QCAR::CameraDevice::getInstance().setFocusMode(QCAR::CameraDevice::FOCUS_MODE_TRIGGERAUTO) ? JNI_TRUE : JNI_FALSE;
}


JNIEXPORT jboolean JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerActivity_setFocusMode(JNIEnv*, jobject, jint mode)
{
    int qcarFocusMode;

    switch ((int)mode)
    {
        case 0:
            qcarFocusMode = QCAR::CameraDevice::FOCUS_MODE_NORMAL;
            break;

        case 1:
            qcarFocusMode = QCAR::CameraDevice::FOCUS_MODE_CONTINUOUSAUTO;
            break;

        case 2:
            qcarFocusMode = QCAR::CameraDevice::FOCUS_MODE_INFINITY;
            break;

        case 3:
            qcarFocusMode = QCAR::CameraDevice::FOCUS_MODE_MACRO;
            break;

        default:
            return JNI_FALSE;
    }

    return QCAR::CameraDevice::getInstance().setFocusMode(qcarFocusMode) ? JNI_TRUE : JNI_FALSE;
}

//Java_min3d_core_Renderer_initRendering

JNIEXPORT void JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerRenderer_initRendering(JNIEnv* env, jobject obj)
{
    LOG("Java_com_mx_ipn_escom_ars_recorrido_ReconocerRenderer_initRendering");
    glClearColor(0.0f, 0.0f, 0.0f, QCAR::requiresAlpha() ? 0.0f : 1.0f);


#ifndef USE_OPENGL_ES_1_1

    shaderProgramID     = SampleUtils::createProgramFromBuffer(cubeMeshVertexShader,
                                                            cubeFragmentShader);

    vertexHandle        = glGetAttribLocation(shaderProgramID,
                                                "vertexPosition");
    normalHandle        = glGetAttribLocation(shaderProgramID,
                                                "vertexNormal");
    textureCoordHandle  = glGetAttribLocation(shaderProgramID,
                                                "vertexTexCoord");
    mvpMatrixHandle     = glGetUniformLocation(shaderProgramID,
                                                "modelViewProjectionMatrix");

#endif

}


JNIEXPORT void JNICALL
Java_com_mx_ipn_escom_ars_recorrido_ReconocerRenderer_updateRendering(
                        JNIEnv* env, jobject obj, jint width, jint height)
{
    LOG("Java_com_mx_ipn_escom_ars_recorrido_ReconocerRenderer_updateRendering");

    // Update screen dimensions
    screenWidth2 = width;
    screenHeight2 = height;

    // Reconfigure the video background
    configureVideoBackground2();
}




#ifdef __cplusplus
}
#endif
