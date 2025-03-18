package deque;

import java.util.Comparator;

public class MaxArrayDeque<witem> extends ArrayDeque<witem> { ;
    private Comparator<witem> arrycomparator;
    witem max;
    public MaxArrayDeque(Comparator<witem> c){
        super();
        arrycomparator = c;
        max = null;
    }
    public witem max(){
        if(size()==0){
            return null;
        }
        if(max == null){
            max=get(0);
            for(int i=1; i<size(); i++){
                if(arrycomparator.compare(max,get(i))>0){
                    max=get(i);
                }
            }
        }
        return max;
    }
    public witem max(Comparator<witem> c){
        if(size()==0){
            return null;
        }
        if(max == null){
            max=get(0);
            for(int i=1; i<size(); i++){
                if(c.compare(max,get(i))<0){
                    max=get(i);
                }
            }
        }
        return max;
    }

    @Override
    public void addFirst(witem d) {
        super.addFirst(d);
        if(max!=null){
            if(arrycomparator.compare(max,d)<0){
                max=d;
            }
        }
    }
    @Override
    public witem removeFirst(){
        witem ret = super.removeFirst();
        if(max == ret){
            max=max();
        }
        return ret;
    }
    @Override
    public void addLast(witem d) {
        super.addLast(d);
        if(max!=null){
            if(arrycomparator.compare(max,d)<0){
                max=d;
            }
        }
    }
    @Override
    public witem removeLast(){
        witem ret = super.removeLast();
        if(max == ret){
            max=max();
        }
        return ret;
    }
}
