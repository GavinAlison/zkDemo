package com.alison.zkjavaapi.zkserial.maker;

/**
 * @Author alison
 * @Date 2019/6/23  9:05
 * @Version 1.0
 * @Description
 */
public class TestIdMaker {
    public static void main(String[] args) throws Exception {
        IdMaker idMaker = new IdMaker("192.168.56.103:2181",
                "/NameService/IdGen", "ID");
        idMaker.start();

        try {
            for (int i = 0; i < 10; i++) {
                String id = idMaker.generateId(IdMaker.RemoveMethod.DELAY);
                System.out.println(id);
            }
        } finally {
            idMaker.stop();
        }
    }
}
