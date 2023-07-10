import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author Duanhe
 * @date 2023/07/10
 */
@WebServlet(name = "AsyncServlet", urlPatterns = "/AsyncServlet", asyncSupported = true)
public class Servlet3Demo2 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long t1 = System.currentTimeMillis();

        // 1.开启异步
        AsyncContext asyncContext = request.startAsync();

        // 2.把我们要执行的代码放到一个独立的线程中，多线程/线程池
        CompletableFuture.runAsync(() -> {
            try {
                doSomeTing(asyncContext, asyncContext.getRequest(), asyncContext.getResponse());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.out.println("async use:" + (System.currentTimeMillis() - t1));
    }

    private void doSomeTing(AsyncContext asyncContext, ServletRequest request, ServletResponse response) throws IOException {

        // 模拟耗时操作
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        response.getWriter().append("async done");

        // 3.业务代码处理完毕，通知结束
        asyncContext.complete();
    }
}

