package min3d;

import com.mx.ipn.escom.ars.recorrido.ArsRenderer;

import min3d.core.Renderer;
import min3d.core.TextureManager;

import android.content.Context;

/**
 * Holds static references to TextureManager, Renderer, and the application Context. 
 */
public class Shared 
{
	private static Context _context;
	private static ArsRenderer _renderer;
	private static Renderer _renderer2;
	private static TextureManager _textureManager;

	
	public static Context context()
	{
		return _context;
	}
	public static void context(Context $c)
	{
		_context = $c;
	}

	public static ArsRenderer renderer()
	{
		return _renderer;
	}
		
	public static void renderer(ArsRenderer $r)
	{
		_renderer = $r;
	}
	
	public static Renderer renderer2()
	{
		return _renderer2;
	}
		
	public static void renderer2(Renderer $r)
	{
		_renderer2 = $r;
	}
	
	
	/**
	 * You must access the TextureManager instance through this accessor
	 */
	public static TextureManager textureManager()
	{
		return _textureManager;
	}
	public static void textureManager(TextureManager $bm)
	{
		_textureManager = $bm;
	}
}
