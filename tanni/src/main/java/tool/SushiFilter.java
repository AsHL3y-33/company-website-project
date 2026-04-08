package tool;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter(urlPatterns= {"/"})
public class SushiFilter implements Filter {
	public void doFilter(
			ServletRequest request, ServletResponse response,
			FilterChain chain
		) throws IOException, ServletException {
			
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charaset=UTF-8");
			
			System.out.println("フィルタの前処理");
			
			chain.doFilter(request, response);
			
			PrintWriter out=response.getWriter();
			out.println("<a href=\"https://endfield.gryphline.com/landing/ua/obt?hg_media=bing&hg_campaign_id=103&msclkid=9e582041580a15d22b6a49cbd108f923\">こちらをクリック</a><br>");
			out.println("filter<br>");
			
			System.out.println("フィルタの後処理");
	}
			public void init(FilterConfig filterConfig) {}
			public void destroy() {}
}
