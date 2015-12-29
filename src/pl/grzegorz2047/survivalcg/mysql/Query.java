package pl.grzegorz2047.survivalcg.mysql;

import pl.grzegorz2047.survivalcg.managers.MysqlManager;

/**
 * Created by grzeg on 23.12.2015.
 */
public abstract class Query {

    protected final MysqlManager mysql;

    public Query(MysqlManager mysql){
        this.mysql = mysql;
    }


}
