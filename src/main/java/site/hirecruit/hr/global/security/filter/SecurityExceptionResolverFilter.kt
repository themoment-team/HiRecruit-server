package site.hirecruit.hr.global.security.filter

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.web.firewall.RequestRejectedException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Filter단에서 발생한 Security Exception을 RestControllerAdvice로 전달하는 Filter
 *
 * @see site.hirecruit.hr.global.security.handler.SecurityExceptionHandler
 * @author 정시원
 * @since 1.0
 */
@WebFilter
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
class SecurityExceptionResolverFilter(
    @Qualifier("handlerExceptionResolver") private val resolver: HandlerExceptionResolver
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (ex: RequestRejectedException) {
            resolver.resolveException(request, response, null, ex)
        } catch (ex: Exception){
            // Security관련된 exception이 아니라면 핸들링 하지 않는다.
            throw ex
        }
    }

}
