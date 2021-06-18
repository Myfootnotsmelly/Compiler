
public enum Keyword {


    IF(258,"if"),
    ELSE(259,"else"),
    THEN(260,"then");

    public  Integer type ;
    public String name;

    private Keyword(Integer type,String name)
    {
        this.name=name;
        this.type=type;
    }


}
