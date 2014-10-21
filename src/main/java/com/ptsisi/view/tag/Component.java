package com.ptsisi.view.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhaoding on 14-10-21.
 */
public class Component {

	protected Map<String, Object> parameters = new LinkedHashMap<String, Object>();

	private ComponentContext context;

	private String theme;

	public Component(ComponentContext context) {
		this.context = context;
	}

	public boolean start(Writer writer) {
		return true;
	}

	public boolean end(Writer writer, String body) {
		try {
			writer.write(body);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected <T extends Component> T findAncestor(Class<T> clazz) {
		return context.find(clazz);
	}

	public void addParameter(String key, Object value) {
		if (key != null) {
			if (value == null) parameters.remove(key);
			else parameters.put(key, value);
		}
	}

	public String parameterString() {
		final StringBuilder sb = new StringBuilder(parameters.size() * 10);
		for (String key : parameters.keySet()) {
			Object value = parameters.get(key);
			sb.append(" ").append(key).append("=\"").append(value.toString()).append("\"");
		}
		return sb.toString();
	}

	public void evaluateParams() {
	}

	public boolean usesBody() {
		return false;
	}
}
