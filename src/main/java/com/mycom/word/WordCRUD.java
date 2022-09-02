package com.mycom.word;

import java.util.ArrayList;
import java.util.Scanner;

public class WordCRUD implements ICRUD {
    ArrayList<Word> list;
    Scanner s;

    WordCRUD(Scanner s) {
        list = new ArrayList<>();
        this.s = s;
    }

    @Override
    public Object add() {

        System.out.print("=> 난이도(1,2,3) & 새 단어 입력 : ");
        //1.drive way
        int level = s.nextInt();
        String word = s.nextLine(); //s.next() -> 엔터는 입력버퍼에 남겨둠

        System.out.print("뜻입력 : ");
        //차고 진입로
        String meaning = s.nextLine();//공백포함 엔터까지 모두 먹음(입력버퍼 완벽히비워줌)

        return new Word(0, level, word, meaning);
    }

    public void addWord() {
        Word one = (Word) add();
        list.add(one);
        System.out.println(" 새 단어가 단어장에 추가되었습니다. ");
    }

    @Override
    public Object update(Object obj) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object delete(Object obj) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void selectOne(int id) {
        // TODO Auto-generated method stub

    }

    public void listALL() {
        System.out.println("------------------------------------------");
        for (int i = 0; i < list.size(); i++) {
            System.out.print((i + 1) + "  ");
            System.out.println(list.get(i).toString());
        }
        System.out.println("------------------------------------------");
    }
}
