package by.aston.bank.servlet;

import by.aston.bank.service.AccountService;
import by.aston.bank.service.dto.AccountWriteDto;
import by.aston.bank.utils.JsonHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/accounts")
public class AccountServlet extends HttpServlet {

    private final AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String strId = req.getParameter("id");
        String response = null;
        if (strId != null) {
            response = JsonHelper.writeAsJson(accountService.findById(Long.parseLong(strId)));
        } else {
            response = JsonHelper.writeAsJson(accountService.findAll());
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        StringBuilder content = new StringBuilder();
        String line = null;
        while ((line = req.getReader().readLine()) != null) {
            content.append(line);
        }
        var createdUser = accountService.create(JsonHelper.readFromJson(content.toString(), AccountWriteDto.class));
        if (createdUser.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(JsonHelper.writeAsJson(createdUser.get()));
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        var strId = req.getParameter("id");
        if (strId == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        StringBuilder content = new StringBuilder();
        String line = null;
        while ((line = req.getReader().readLine()) != null) {
            content.append(line);
        }
        var updatedUser = accountService.update(Long.parseLong(strId),
                JsonHelper.readFromJson(content.toString(), AccountWriteDto.class));
        if (updatedUser.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(JsonHelper.writeAsJson(updatedUser.get()));
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var strId = req.getParameter("id");
        if (strId == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (accountService.delete(Long.parseLong(strId))) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
