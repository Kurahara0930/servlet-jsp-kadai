package forms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String phoneNumber = request.getParameter("phone_number");
		
		name = Objects.toString(name, "");
		email = Objects.toString(email, "");
		address = Objects.toString(address, "");
		phoneNumber = Objects.toString(phoneNumber, "");
		
		ArrayList<String> errorList = new ArrayList<String>();
		
		// 氏名のバリデーション
		if("".equals(name.trim())) {
			errorList.add("氏名が未入力です");
		}
		
		// メールアドレスのバリデーション
		if(!"".equals(email.trim())) {
			if(!email.matches("^[a-zA-Z0-9.]+@[a-zA-Z0-9.]+$")) {
				errorList.add("メールアドレスの形式が不正です");
			}
		} else {
			errorList.add("メールアドレスが未入力です");
		}
		
		// 住所のバリデーション
		if(!"".equals(address.trim())) {
			if(!address.matches("^.{0,3}[都道府県].*")) {
				errorList.add("都道府県から入力してください");
			}
		} else {
			errorList.add("住所が未入力です");
		}
		
		// 電話番号のバリデーション
		if(!"".equals(phoneNumber.trim())) {
			if(!phoneNumber.matches("^0[0-9]{2}-[0-9]{4}-[0-9]{4}")) {
				errorList.add("電話番号の形式が正しくありません");
			}
		} else {
			errorList.add("電話番号が未入力です");
		}
		
		// エラーメッセージがあれば設定
		if(!errorList.isEmpty()) {
			request.setAttribute("errorList", errorList);
		}
		
		request.setAttribute("name", name);
		request.setAttribute("email", email);
		request.setAttribute("address", address);
		request.setAttribute("phoneNumber", phoneNumber);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/confirmPage.jsp");
		dispatcher.forward(request, response);
	}
}
