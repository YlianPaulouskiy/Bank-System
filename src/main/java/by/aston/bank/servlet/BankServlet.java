package by.aston.bank.servlet;

import by.aston.bank.service.BankService;
import by.aston.bank.service.dto.BankWriteDto;
import by.aston.bank.utils.JsonHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/banks")
public class BankServlet extends HttpServlet {

    private final BankService bankService = new BankService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String strId = req.getParameter("id");
        String response = null;
        if (strId != null) {
            response = JsonHelper.writeAsJson(bankService.findById(Long.parseLong(strId)));
        } else {
            response = JsonHelper.writeAsJson(bankService.findAll());
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
        var createdUser = bankService.create(JsonHelper.readFromJson(content.toString(), BankWriteDto.class));
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
        var updatedUser = bankService.update(Long.parseLong(strId),
                JsonHelper.readFromJson(content.toString(), BankWriteDto.class));
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
        if (bankService.delete(Long.parseLong(strId))) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
