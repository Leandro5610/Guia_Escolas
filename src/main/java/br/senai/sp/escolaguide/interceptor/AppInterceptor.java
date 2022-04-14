package br.senai.sp.escolaguide.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.senai.sp.escolaguide.annotation.Publico;

@Component
public class AppInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
		// variavel para descobri para onde estao tentando ir
		String uri = request.getRequestURI();
		System.out.println(uri);
		//veridfica se handler é um HandleMethod que indica que encontrou um metodo em algum controller
		
		if(handler instanceof HandlerMethod) {
			//liberar o aceso a pagina inicial
			if(uri.equals("/")) {
				return true;
			}
			// fazer o casting para HeadlerMethod
			HandlerMethod metodoChamado = (HandlerMethod) handler;
			//se o metodo for publico libera
			if(metodoChamado.getMethodAnnotation(Publico.class)!= null) {
				return true;
			}
			if(uri.endsWith("/error")) {
				return true;
			}
			
			// verifica se existe um usuario logado
			if(request.getSession().getAttribute("admLogado")!= null) {
				return true;
			}else {
				response.sendRedirect("/");
				return false;
			}
			
			
		}
		return true;
	}
}
