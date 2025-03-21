package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> { ;
    private Comparator<T> arrycomparator;
    private T max;
    public MaxArrayDeque(Comparator<T> c){
        super();
        arrycomparator = c;
        max = null;
    }
    public T max(){
        if(size()==0){
            return null;
        }
        max=get(0);
        for(int i=1; i<size(); i++){
            if(arrycomparator.compare(max,get(i))<0){
                max=get(i);
            }
        }
        return max;
    }
    public T max(Comparator<T> c){
        if(size()==0){
            return null;
        }
        max=get(0);
        for(int i=1; i<size(); i++){
            if(c.compare(max,get(i))<0){
                max=get(i);
            }
        }
        return max;
    }

//    @Override
//    public void addFirst(T d) {
//        super.addFirst(d);
//        if(max!=null){
//            if(arrycomparator.compare(max,d)<0){
//                max=d;
//            }
//        }
//    }
//    @Override
//    public T removeFirst(){
//        T ret = super.removeFirst();
//        if(max == ret){
//            max=max();
//        }
//        return ret;
//    }
//    @Override
//    public void addLast(T d) {
//        super.addLast(d);
//        if(max!=null){
//            if(arrycomparator.compare(max,d)<0){
//                max=d;
//            }
//        }
//    }
//    @Override
//    public T removeLast(){
//        T ret = super.removeLast();
//        if(max == ret){
//            max=max();
//        }
//        return ret;
//    }
}
