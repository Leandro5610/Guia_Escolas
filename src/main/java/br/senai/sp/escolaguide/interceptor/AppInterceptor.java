package br.senai.sp.escolaguide.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.senai.sp.escolaguide.annotation.Privado;
import br.senai.sp.escolaguide.annotation.Publico;
import br.senai.sp.escolaguide.model.Usuario;
import br.senai.sp.escolaguide.rest.UsuarioRestController;

@Component
public class AppInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// variavel para descobri para onde estao tentando ir
		String uri = request.getRequestURI();
		System.out.println(uri);
		// veridfica se handler é um HandleMethod que indica que encontrou um metodo em
		// algum controller

		if (handler instanceof HandlerMethod) {
			// liberar o aceso a pagina inicial
			if (uri.equals("/")) {
				return true;
			}

			// fazer o casting para HeadlerMethod
			HandlerMethod metodoChamado = (HandlerMethod) handler;

			if (uri.startsWith("/api")) {
				// variavel para token
				String token = null;
				// quando for API
				// se for metodo privado
				if (metodoChamado.getMethodAnnotation(Privado.class) != null) {
					try {
						// obtem o token da request

						token = request.getHeader("Authorization");

						// algoritimo para descriptografar
						Algorithm algoritimo = Algorithm.HMAC256(UsuarioRestController.SECRET);
						// objeto para verificar o token
						JWTVerifier verifier = JWT.require(algoritimo).withIssuer(UsuarioRestController.EMISSOR)
								.build();
						// validar o token
						DecodedJWT decoded = verifier.verify(token);
						// extrair os dados do payload
						Map<String, Claim> payload = decoded.getClaims();
						
						return true;
					} catch (Exception e) {
						if(token == null) {
							response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
						}else {
							response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
						}
						return false;  
					}
				}
				return true;

			} else {
				// se o metodo for publico libera
				if (metodoChamado.getMethodAnnotation(Publico.class) != null) {
					return true;
				}
				if (uri.endsWith("/error")) {
					return true;
				}

				// verifica se existe um usuario logado
				if (request.getSession().getAttribute("admLogado") != null) {
					return true;
				} else {
					response.sendRedirect("/");
					return false;
				}

			}
		}
		return true;
	}
}
