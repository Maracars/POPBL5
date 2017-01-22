package action.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.HibernateGeneric;
import domain.model.users.Admin;
import domain.model.users.Airline;
import domain.model.users.Controller;
import domain.model.users.Mantainance;
import domain.model.users.Passenger;
import domain.model.users.User;

/**
 * <h3>The Class UserListJSONAction.</h3>
 * <p>
 * Action that returns a user list as json according to request data from
 * Datatables. For more information see datatables serverside processing.
 * <p>
 * 
 * @param <sincronized>
 *            the generic type
 */
public class UserListJSONAction<sincronized> extends ActionSupport {

	private static final String BLANKSPACE = " ";

	private static final String STRING_ASC = "asc";

	private static final String STRING_DESC = "desc";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The start. */
	private Integer draw = 0;
	private Integer recordsTotal = 1;
	private Integer recordsFiltered = 0;
	private Integer length = 0;
	private Integer start = 0;

	/** The data. */
	private List<UserView> data = new ArrayList<UserView>();

	/** The error. */
	String error = null;

	@Override
	public synchronized String execute() throws Exception {

		Map<String, String[]> map = ActionContext.getContext().getParameters().toMap();

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

	/**
	 * Filter.
	 *
	 * @param data
	 *            the data
	 * @param search
	 *            the search
	 * @return the list
	 */
	private List<UserView> filter(List<UserView> data, String search) {
		String searchToLower = search.toLowerCase();
		for (Iterator<UserView> uIt = data.iterator(); uIt.hasNext();) {
			UserView uv = uIt.next();
			if (uv.getName().toLowerCase().contains(searchToLower))
				continue;
			if (uv.getUsername().toLowerCase().contains(searchToLower))
				continue;
			if (uv.getType().toLowerCase().contains(searchToLower))
				continue;
			uIt.remove();
		}
		return data;
	}

	/**
	 * Sort.
	 *
	 * @param data
	 *            the data
	 * @param orderCol
	 *            the order col
	 * @param orderDir
	 *            the order dir
	 * @return the list
	 */
	private List<UserView> sort(List<UserView> data, int orderCol, String orderDir) {
		switch (orderCol) {
		case 1:
			if (orderDir.equals(STRING_ASC))
				data.sort((UserView uv1, UserView uv2) -> uv1.getType().compareToIgnoreCase(uv2.getType()));
			if (orderDir.equals(STRING_DESC))
				data.sort((UserView uv1, UserView uv2) -> -uv1.getType().compareToIgnoreCase(uv2.getType()));
			break;
		case 2:
			if (orderDir.equals(STRING_ASC))
				data.sort((UserView uv1, UserView uv2) -> uv1.getUsername().compareToIgnoreCase(uv2.getUsername()));
			if (orderDir.equals(STRING_DESC))
				data.sort((UserView uv1, UserView uv2) -> -uv1.getUsername().compareTo(uv2.getUsername()));
			break;
		case 3:
			if (orderDir.equals(STRING_ASC))
				data.sort((UserView uv1, UserView uv2) -> uv1.getName().compareToIgnoreCase(uv2.getName()));
			if (orderDir.equals(STRING_DESC))
				data.sort((UserView uv1, UserView uv2) -> -uv1.getName().compareToIgnoreCase(uv2.getName()));
			break;
		default:
			data.sort((UserView uv1, UserView uv2) -> uv1.getType().compareToIgnoreCase(uv2.getType()));
			break;
		}
		return data;
	}

	/**
	 * Generate data.
	 *
	 * @return the list
	 */
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
				name = ((Passenger) user).getName() + BLANKSPACE + ((Passenger) user).getSecondName();
			if (user instanceof Mantainance)
				name = ((Mantainance) user).getName() + BLANKSPACE + ((Mantainance) user).getSecondName();
			if (user instanceof Controller)
				name = ((Controller) user).getName() + BLANKSPACE + ((Controller) user).getSecondName();
			if (user instanceof Admin)
				name = ((Admin) user).getName() + BLANKSPACE + ((Admin) user).getSecondName();

			usrs.add(new UserView(username, name, type));

		}

		return usrs;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public List<UserView> getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data
	 *            the new data
	 */
	public void setData(List<UserView> data) {
		this.data = data;
	}

	/**
	 * Gets the draw.
	 *
	 * @return the draw
	 */
	public Integer getDraw() {
		return draw;
	}

	/**
	 * Sets the draw.
	 *
	 * @param draw
	 *            the new draw
	 */
	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	/**
	 * Gets the records total.
	 *
	 * @return the records total
	 */
	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	/**
	 * Sets the records total.
	 *
	 * @param recordsTotal
	 *            the new records total
	 */
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	/**
	 * Gets the records filtered.
	 *
	 * @return the records filtered
	 */
	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	/**
	 * Sets the records filtered.
	 *
	 * @param recordsFiltered
	 *            the new records filtered
	 */
	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public Integer getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 *
	 * @param length
	 *            the new length
	 */
	public void setLength(Integer length) {
		this.length = length;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 *
	 * @param start
	 *            the new start
	 */
	public void setStart(Integer start) {
		this.start = start;
	}

	/**
	 * The Class UserView.
	 */
	public class UserView {

		/** The type. */
		String type;

		/** The username. */
		String username;

		/** The name. */
		String name;

		/**
		 * Instantiates a new user view.
		 *
		 * @param username
		 *            the username
		 * @param name
		 *            the name
		 * @param type
		 *            the type
		 */
		public UserView(String username, String name, String type) {
			this.username = username;
			this.name = name;
			this.type = type;
		}

		/**
		 * Gets the type.
		 *
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * Sets the type.
		 *
		 * @param type
		 *            the new type
		 */
		public void setType(String type) {
			this.type = type;
		}

		/**
		 * Gets the username.
		 *
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * Sets the username.
		 *
		 * @param username
		 *            the new username
		 */
		public void setUsername(String username) {
			this.username = username;
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Sets the name.
		 *
		 * @param name
		 *            the new name
		 */
		public void setName(String name) {
			this.name = name;
		}

	}

}
