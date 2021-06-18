import java.util.*;

public class Parser {
    //单目算符和双目算符的前缀
    private static Character[] op_code = {'=','-','*','/',':','!','<','>','|','&'};

   // private List<String> keyword = Arrays.stream(Keyword.values()).map(x->x.name).collect(Collectors.toList());
    private static Map<String,Integer> keyword = new HashMap<>();

    private static List<Character> op;

    static{
        for(Keyword k:Keyword.values())
        {
            keyword.put(k.name,k.type);
        }
        op = Arrays.asList(op_code);
    }
    //标识符表
    public List<Form> ids = new ArrayList<>();

    //常量表
    public List<Form> constants = new ArrayList<>();

    //token表
    public List<Token> tokens =  new ArrayList<>();

    //指针
    private int ptr = 0;

    //当前读出字符
    private char ch ;

    //输入串
    public String str;

    private StringBuffer parseStr;

    //行数,以后用到
    private int line = 0;

    public void parse(String str){
        this.str = str;
        getNext();
        while(ch!='#'){
            //预处理空白
            while(isBlank()){
                getNext();
            }

            //识别关键字和标识符
            if(Character.isLetter(ch)){
                parseStr = new StringBuffer();parseStr.append(ch);
                getNext();
                while(Character.isLetter(ch)||Character.isDigit(ch)){
                    parseStr.append(ch);
                    getNext();
                }
                lookupLetter();
                continue;
            }

            //识别数字
            else if(Character.isDigit(ch)){
                parseStr = new StringBuffer();parseStr.append(ch);
                getNext();
                while(Character.isDigit(ch)){
                    parseStr.append(ch);
                    getNext();
                }
                lookupDigit();
                continue;
            }

            //识别算符
            else if(op.contains(ch)){
                if(ch=='<'||ch=='>'||ch==':'||ch=='!'||ch=='='){
                    if(readNext()=='='){
                        tokens.add(new Token(null, ch +"=",null));
                        getNext();        getNext();
                        continue;
                    }
                    if(ch==':'||ch=='!')
                        throw new SyntaxException("词法错误：孤立的:或！");
                }else if(ch=='|'){
                    if(readNext()=='|')
                    {
                        tokens.add(new Token(null, ch +"|",null));
                        getNext();        getNext();
                        continue;
                    }
                    throw new SyntaxException("词法错误：孤立的|");
                }else if(ch=='&'){
                    if(readNext()=='&')
                    {
                        tokens.add(new Token(null, ch +"&",null));
                        getNext();        getNext();
                        continue;
                    }
                    throw new SyntaxException("词法错误：孤立的&");
                }
            }
            throw new SyntaxException("未能识别的符号:"+ch);

        }
        showTokens();
    }

    /**
     * 读字符，指针前移
     * @return
     */
    private char getNext(){
        ch = str.charAt(ptr++);
        return ch;
    }

    /**
     * 预读字符，指针不移动
     * @return
     */
    private char readNext(){
        return str.charAt(ptr);
    }

    private void rollback(){
        ptr--;
        ch=' ';
    }

    private boolean isBlank(){
        return ch==' '||ch=='\r'; //空格或换行
    }

    /**
     * 识别出一个标识符或关键字，查关键字表
     */
    private void lookupLetter(){
        Integer type = (Integer) keyword.get(parseStr.toString());
        if(type!=null){
            tokens.add(new Token(type,parseStr.toString(),null));
            return;
        }
        Form id = new Form(parseStr.toString(),null);

        int entry = ids.indexOf(id);
        if(entry==-1){
            tokens.add(new Token(Typenum.ID,parseStr.toString(),ids.size()));
            ids.add(id);
        }
        else{
            tokens.add(new Token(Typenum.ID,parseStr.toString(),entry));
        }
    }


    /**
     * 识别出常量，查常量表
     */
    private void lookupDigit(){
        Form constant = new Form(parseStr.toString(),null);
        int entry = constants.indexOf(constant);
        if(entry==-1){
            tokens.add(new Token(Typenum.NUM,parseStr.toString(),constants.size()));
            constants.add(constant);
        }
        else{
            tokens.add(new Token(Typenum.NUM,parseStr.toString(),entry));
        }

    }


    /**
     * 打印token表
     */
    private void showTokens(){
        tokens.forEach(x-> System.out.println(x));
    }
}
