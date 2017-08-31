package com.kylin.utils.base;

import com.google.common.base.Optional;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.Map;

/**
 * Created by thonny on 2015-5-14.
 */
public class CheckUtil {


    public static void main(String[] args) {
        Optional<Integer> possible=Optional.of(6);
        Optional<Integer> absentOpt=Optional.absent();
        Optional<Integer> NullableOpt=Optional.fromNullable(null);
        Optional<Integer> NoNullableOpt=Optional.fromNullable(10);
        if(possible.isPresent()){
            System.out.println("possible isPresent:"+possible.isPresent());
            System.out.println("possible value:"+possible.get());
        }
        if(absentOpt.isPresent()){
            System.out.println("absentOpt isPresent:"+absentOpt.isPresent()); ;
        }
        if(NullableOpt.isPresent()){
            System.out.println("fromNullableOpt isPresent:"+NullableOpt.isPresent()); ;
        }
        if(NoNullableOpt.isPresent()){
            System.out.println("NoNullableOpt isPresent:"+NoNullableOpt.isPresent()); ;
        }
    }

    public static <T> boolean checkNullOrEmpty(T t){
        boolean b = true;
        if (t==null)
            return b;
        if(t instanceof Collection<?>){
            if(((Collection) t).size()>0 && t!=null){
                b = false;
            }
        }
        if(t instanceof String){
            if(((String) t).length()>0 && t!=null){
                b = false;
            }
        }
        if(t instanceof Map){
            if(((Map)t).size()>0 &&t!=null ){
                b = false;
            }
        }
        if(t.getClass().isArray()){
            if (((String[]) t).length>0&&t!=null){
                b = false;
            }
        }
        return b;
    }

    /**
     * rop 入参检查
     * @param params
     * @param conditions
     * @return
     */
    public static boolean paramsCheck(Map<String,String[]> params,String[] conditions){

        for (String condition : conditions) {
            if(checkNullOrEmpty(params.get(condition)))
                return false;
        }
        return true;
    }

    /**
     * dubbo 入参检查
     * @param params
     * @param conditions
     * @return true 通过校验  false 未通过校验
     */
    public static boolean paramsChecks(Map<String,String> params,String[] conditions){
        for (String condition : conditions) {
            if(checkNullOrEmpty(params.get(condition)))
                return false;
        }
        return true;
    }

    /**
     * murmurhash算法
     */
    public static Long hash(String key) {

        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);

        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(
                    ByteOrder.LITTLE_ENDIAN);
            // for big-endian version, do this first:
            // finish.position(8-buf.remaining());
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        buf.order(byteOrder);
        return h;
    }

}
