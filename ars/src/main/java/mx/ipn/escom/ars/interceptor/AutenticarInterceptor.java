package mx.ipn.escom.ars.interceptor;

import mx.ipn.escom.ars.util.NombreObjetosSesion;

import org.apache.log4j.Logger;
//import org.directwebremoting.WebContextFactory;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AutenticarInterceptor implements Interceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1939367355017016797L;
	/**
	 * 
	 */
		
	private String prevAction;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		String previous = null;		
		if (actionInvocation.getProxy().getActionName().equals("login")) {
			actionInvocation.invoke();
			if (ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.USUARIO) == null) {
				return Action.LOGIN;
			} else {
				return "next";
			}

		} else  {
			if (ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.USUARIO) == null) {				

				setPrevAction(actionInvocation.getProxy().getActionName());

				ActionContext.getContext().getSession()
						.put("prevAction", getPrevAction());
				return Action.LOGIN;
			} else {			
				previous = (String) ActionContext.getContext().getSession()
						.get("prevAction");
				if (previous != null) {
					ActionContext.getContext().getSession()
							.put("current", previous);
					ActionContext.getContext().getSession()
							.put("prevAction", null);
					return "next";
				} else
					return actionInvocation.invoke();

			}
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	public String getPrevAction() {
		return prevAction;
	}

	public void setPrevAction(String prevAction) {
		this.prevAction = prevAction;
	}

}
