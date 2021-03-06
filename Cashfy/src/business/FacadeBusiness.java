package business;

import business.control.FinancialOptionControl;
import business.control.NewsControl;
import business.control.UserControl;
import java.util.Map;
import java.util.HashMap;

import business.control.WalletControl;
import business.control.command.*;
import util.*;

/**
 * Fachada para os serviços de NewsControl e UserControl em Business Implementa
 * o padrão de projeto Facade e Singleton
 */
public class FacadeBusiness {

	private static FacadeBusiness instance = null;

	private UserControl uc;
	private NewsControl nc;
	private WalletControl wc;

	private FinancialOptionControl fc;
	private Map<String, Command> foCommands;

	private FacadeBusiness() throws InfraException {
		this.uc = new UserControl();
		this.nc = new NewsControl();
		this.wc = new WalletControl();
		this.fc = new FinancialOptionControl();

		foCommands = new HashMap<>();
		foCommands.put("add", new AddFOCommand(fc));
		foCommands.put("search", new SearchFOCommand(fc));
		foCommands.put("update", new UpdateFOCommand(fc));
		foCommands.put("undo", new UndoFOUpdateCommand(fc));
		foCommands.put("save", new SaveFOCommand(fc));
	}

	/**
	 * Padrão de projeto: Singleton Garantir que exista apenas um objeto de
	 * FacadeBusiness
	 * 
	 * @return A única instância de FacadeBusiness, no caso desta ser nula cria
	 *         uma nova instância
	 * @throws InfraException Em caso de erro durante a leitura da base de dados
	 */
	public static FacadeBusiness getInstance() throws InfraException {
		if (instance == null) {
			synchronized (FacadeBusiness.class) {
				if (instance == null) {
					instance = new FacadeBusiness();
				}
			}
		}

		return instance;
	}

	public void addUser(String... params)
			throws InvalidUsernameException, InvalidPasswordException, InvalidAddException {
		uc.add(params);
	}

	public void listAllUsers() {
		uc.listAll();
	}

	public String listUser(String login) throws InvalidUsernameException {
		return uc.list(login);
	}

	public void delUser(String login) throws InvalidUsernameException {
		uc.del(login);
	}

	public void saveUsers() throws InfraException {
		uc.save();
	}

	public void addNews(String[] params) throws InvalidAddException {
		nc.add(params);
	}

	public void listAllNews() {
		nc.listAll();
	}

	public String listNews(String title) {
		return nc.list(title);
	}

	public void saveNews() throws InfraException {
		nc.save();
	}

	public Object executeFOOperation(String op, String[] params) throws UnsuccessfulOperationException, InfraException {
		Command c = foCommands.get(op);
		return c.execute(params);
	}

	public void listAllWallets() {
		wc.listAll();
	}

	public String listWallet(String login) throws InvalidAssetException {
		return wc.list(login);
	}

	public void addWallet(String[] params) throws InvalidAddException {
		wc.add(params);
	}

	public void delWallet(String login) throws InvalidAssetException {
		wc.del(login);
	}

	public void saveWallets() throws InfraException {
		wc.save();
	}
}
