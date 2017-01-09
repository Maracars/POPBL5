package action.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.users.Admin;
import domain.model.users.Airline;
import domain.model.users.Controller;
import domain.model.users.Mantainance;
import domain.model.users.Passenger;
import domain.model.users.User;

public class UserListJSONAction<sincronized> extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private Integer draw = 0, recordsTotal = 1, recordsFiltered = 0, length = 0, start = 0;
	private List<UserView> data = new ArrayList<UserView>();
	String error = null;

	@Override
	public synchronized String execute() throws Exception {

		Map<String, String[]> map = ActionContext.getContext().getParameters().toMap();
		for (Entry<String, String[]> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			System.out.println("key:" + key + " value: " + Arrays.toString((String[]) value));
		}

		String search = map.get("search[value]")[0];
		int orderCol = Integer.parseInt(map.get("order[0][column]")[0]);
		String orderDir = map.get("order[0][dir]")[0];

		data = generateData();
		recordsTotal = data.size();

		data = filter(data, search);
		data = sort(data, orderCol, orderDir);
		recordsFiltered = data.size();
		if (data.size() != 0)
			data = data.subList(start, (start + length > data.size()) ? data.size() : start + length);
		data = new ArrayList<UserView>(data);
		return SUCCESS;
	}

	private List<UserView> filter(List<UserView> data, String search) {
		search = search.toLowerCase();
		for (Iterator<UserView> uIt = data.iterator(); uIt.hasNext();) {
			UserView uv = uIt.next();
			if (uv.getName().toLowerCase().contains(search))
				continue;
			if (uv.getUsername().toLowerCase().contains(search))
				continue;
			if (uv.getType().toLowerCase().contains(search))
				continue;
			uIt.remove();
		}
		return data;
	}

	private List<UserView> sort(List<UserView> data, int orderCol, String orderDir) {
		switch (orderCol) {
		case 1:
			if (orderDir.equals("asc"))
				data.sort((UserView uv1, UserView uv2) -> uv1.getType().compareToIgnoreCase(uv2.getType()));
			if (orderDir.equals("desc"))
				data.sort((UserView uv1, UserView uv2) -> -uv1.getType().compareToIgnoreCase(uv2.getType()));
			break;
		case 2:
			if (orderDir.equals("asc"))
				data.sort((UserView uv1, UserView uv2) -> uv1.getUsername().compareToIgnoreCase(uv2.getUsername()));
			if (orderDir.equals("desc"))
				data.sort((UserView uv1, UserView uv2) -> -uv1.getUsername().compareTo(uv2.getUsername()));
			break;
		case 3:
			if (orderDir.equals("asc"))
				data.sort((UserView uv1, UserView uv2) -> uv1.getName().compareToIgnoreCase(uv2.getName()));
			if (orderDir.equals("desc"))
				data.sort((UserView uv1, UserView uv2) -> -uv1.getName().compareToIgnoreCase(uv2.getName()));
			break;
		default:
			data.sort((UserView uv1, UserView uv2) -> uv1.getType().compareToIgnoreCase(uv2.getType()));
			break;
		}
		return data;
	}

	public List<UserView> generateData() {
		ArrayList<Object> users = (ArrayList<Object>) HibernateGeneric.loadAllObjects(new User());
		ArrayList<UserView> usrs = new ArrayList<>();
		for (Object u : users) {
			User user = (User) u;

			String username = user.getUsername();
			String type = user.getClass().getSimpleName().toLowerCase();
			type = getText("global." + type);

			String name = null;
			if (user instanceof Airline)
				name = ((Airline) user).getName();
			if (user instanceof Passenger)
				name = ((Passenger) user).getName() + " " + ((Passenger) user).getSecondName();
			if (user instanceof Mantainance)
				name = ((Mantainance) user).getName() + " " + ((Mantainance) user).getSecondName();
			if (user instanceof Controller)
				name = ((Controller) user).getName() + " " + ((Controller) user).getSecondName();
			if (user instanceof Admin)
				name = ((Admin) user).getName() + " " + ((Admin) user).getSecondName();

			usrs.add(new UserView(username, name, type));

		}

		return usrs;
	}

	public List<UserView> getData() {
		return data;
	}

	public void setData(List<UserView> data) {
		this.data = data;
	}

	public class UserView {
		String type;
		String username;
		String name;

		public UserView(String username, String name, String type) {
			this.username = username;
			this.name = name;
			this.type = type;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

}
