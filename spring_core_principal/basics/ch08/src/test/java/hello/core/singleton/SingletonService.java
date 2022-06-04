package hello.core.singleton;

public class SingletonService {

    //JVM 로딩 시 자기자신이 생성됨
    private static final SingletonService instance = new SingletonService();

    //조회시 사용
    public static SingletonService getInstance(){
        return instance;
    }

    //외부 접근을 막음
    private SingletonService(){

    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
