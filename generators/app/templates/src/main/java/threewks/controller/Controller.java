package threewks.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.threewks.thundr.http.ContentType;
import com.threewks.thundr.http.StatusCode;
import com.threewks.thundr.http.exception.HttpStatusException;
import com.threewks.thundr.logger.Logger;
import com.threewks.thundr.view.View;
import com.threewks.thundr.view.string.StringView;

import javax.servlet.ServletContext;


public class Controller {

	private ServletContext context;

	public Controller(ServletContext context) {
		this.context = context;
	}

	public View index() {
		String html = loadHtmlAsString("/index.html");
		return new StringView(html).withContentType(ContentType.TextHtml);
	}

	private String loadHtmlAsString(String path) {
		try (InputStream input = context.getResourceAsStream(path)) {
			Logger.info("%s", input);
			Scanner s = new Scanner(input).useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		} catch (IOException e) {
			throw new HttpStatusException(e, StatusCode.InternalServerError, "Error loading %s", path);
		}
	}
}
