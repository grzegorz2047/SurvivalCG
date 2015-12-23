package pl.grzegorz2047.survivalcg.mysql;

import pl.grzegorz2047.survivalcg.SurvivalCG;

/**
 * Created by grzeg on 23.12.2015.
 */
public abstract class Query {

    protected final Mysql mysql;

    public Query(Mysql mysql){
        this.mysql = mysql;
    }

}
