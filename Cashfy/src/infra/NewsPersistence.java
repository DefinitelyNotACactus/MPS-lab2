package infra;

import business.model.News;

/** Classe que implementa a persistência de dados de notícias (i.e. leitura e escrita da base de dados do sistema)
 * Implementa o padrão de projeto Singleton
 */
public class NewsPersistence extends Persistence<String, News> {

    private static NewsPersistence instance;

    private NewsPersistence() {
        super("./database/news.ser");
    }

    /** Padrão de projeto: Singleton
     * Garantir que exista apenas um objeto do tipo UserPersistence
     * @return A única instância de NewsPersistence
     */
    public static NewsPersistence getInstance() {
        if(instance == null) {
        	synchronized (NewsPersistence.class ) {
				if(instance == null) {
					instance = new NewsPersistence();
				}
			}
        }
    	
    	return instance;
    }
}
