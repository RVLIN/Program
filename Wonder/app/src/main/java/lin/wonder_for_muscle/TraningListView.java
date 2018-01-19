package lin.wonder_for_muscle;

/**
 * Created by Kuma on 12/14/2017.
 */

public class TraningListView {
    private String name;
    private String time;
    private Integer imvg;
    public TraningListView(String name,String time,Integer imvg)
    {
        this.imvg=imvg;
        this.name=name;
        this.time=time;
    }
    public int getimvg(){
        return imvg;
    }
    public void setImvg(Integer imvg){
        this.imvg = imvg;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;
    }
}
