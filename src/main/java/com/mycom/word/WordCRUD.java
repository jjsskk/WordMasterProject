package com.mycom.word;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.SimpleTimeZone;


public class WordCRUD implements ICRUD {
    ArrayList<Word> list;
    Scanner s;
    final String fname = "Dictionary.txt";

    WordCRUD(Scanner s) {
        list = new ArrayList<>();
        this.s = s;
    }


    public Object add() {
        System.out.print("\n=> 난이도(1,2,3,4,5) & 새 단어 입력 : ");
        //1.drive way
        int level = s.nextInt();
        String word = s.nextLine(); //s.next() -> 엔터는 입력버퍼에 남겨둠

        System.out.print("뜻입력 : ");
        //차고 진입로
        String meaning = s.nextLine();//공백포함 엔터까지 모두 먹음(입력버퍼 완벽히비워줌)

        return new Word(0, level, word, meaning);
    }

    @Override
    public void addItem() {
        Word one = (Word) add();
        list.add(one);

        System.out.println("\n새 단어가 단어장에 추가되었습니다!!! ");
    }


    public void listALL() {
        final int size = list.size();
        System.out.println("\n------------------------------------------");
        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + "  ");
            System.out.println(list.get(i).toString());
        }
        System.out.println("------------------------------------------");
    }
    public ArrayList<Integer> listALL(String keyword) {
        ArrayList<Integer> idlist = new ArrayList<>();
        int j=0;
        final int size = list.size();
        System.out.println("\n------------------------------------------");
        for (int i = 0; i < size; i++) {
            String word =list.get(i).getWord();
            if(!word.contains(keyword)) continue;
            System.out.print((j + 1) + "  ");
            System.out.println(list.get(i).toString());
            idlist.add(i);
            j++;
        }
        System.out.println("------------------------------------------");
        return idlist;
    }

    public void listAll(int level){
        int j=0;
        final int size = list.size();
        System.out.println("\n------------------------------------------");
        for (int i = 0; i < size; i++) {
            int ilevel =list.get(i).getLevel();
            if(ilevel != level) continue;
            System.out.print((j + 1) + "  ");
            System.out.println(list.get(i).toString());
            j++;
        }
        System.out.println("------------------------------------------");
    }

    @Override
    public void updateItem() {//s.nextInt()나 s.next() 다음에는 s.nextLine()을 넣어 찌꺼기를 소거하기.
        System.out.print("=> 수정할 단어 검색: ");
        String keyword = s.next();//공백을 허용하지 않기위해서 사용
        ArrayList<Integer> idlist = listALL(keyword);
        System.out.print("=> 수정할 번호 선택 : ");
        int id = s.nextInt();//엔터가 버퍼에 남음
        s.nextLine();//남은 엔터 없애기위해서
        System.out.print("=> 뜻 입력 : ");
        String meaning = s.nextLine();//엔터와 공백까지 모두받음
        Word word = list.get(idlist.get(id-1));//id=j+1
        word.setMeaning(meaning);
        System.out.println("\n단어가 성공적으로수정되었습니다. ");

    }

    @Override
    public void deleteItem() {
        System.out.print("=> 삭제할 단어 검색: ");
        String keyword = s.next();//공백을 허용하지 않기위해서 사용
        ArrayList<Integer> idlist = listALL(keyword);
        System.out.print("=> 삭제할 번호 선택 : ");
        int id = s.nextInt();//엔터가 버퍼에 남음
        s.nextLine();//남은 엔터 없애기위해서
        System.out.print("=> 정말로 삭제하실래요?(Y/N) ");
        String ans= s.next();
        if(ans.equalsIgnoreCase("Y")){
            list.remove((int) idlist.get(id-1));//Integer->int
            System.out.println("\n단어가 성공적으로 삭제되었습니다. ");
        }else{
            System.out.println("\n취소되었습니다. ");
        }
    }
    @Override
    public void loadFile(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(fname));
            String line;
            int count = 0;
            while (true){
                line = br.readLine();//한라인을 읽어옴
                if(line==null) break;//파일의 끝

                String[] data = line.split("\\|");//원래 정규식이 들어가기때문에 |하나로는 문자로 인식이안됨
                int level = Integer.parseInt(data[0]);
                String word = data[1];
                String meaning  = data[2];
                list.add(new Word(0,level,word,meaning));
                count++;
            }
            br.close();
            System.out.println("==> "+count+"개 데이터 로딩 완료!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveFile() {
        try {
//            PrintWriter pr =new PrintWriter(new FileWriter("test.txt")); //test
            PrintWriter pr =new PrintWriter(new FileWriter(fname)); //test
            for(Word one : list){
                pr.write(one.toFileString()+"\n");//write는 기본적으로 엔터를 뒤에 안넣어줌
            }

            pr.close();
            System.out.println("\n==> 데이터 저장 완료!!!");

        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    @Override
    public void searchLevel() {

        System.out.print("=> 원하는 레벨은? (1:초급, 2:중급, 3:고급, 4:초고급, 5:원어민): ");
        int level = s.nextInt();
        listAll(level);
    }

    @Override
    public void searchWord() {
        System.out.print("=> 검색할 단어 입력: ");
        String keyword = s.next();//공백을 허용하지 않는 문자열 입력 받음
        listALL(keyword);//next()-> nextint()(버퍼에서 int만 뽑아먹음)는 next가 엔터버퍼에 남겨도 오류없음
    }
}
