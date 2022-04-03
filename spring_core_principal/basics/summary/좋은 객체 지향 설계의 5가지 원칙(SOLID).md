# 좋은 객체 지향 설계의 5가지 원칙(SOLID)

클린코드로 유명한 로버트 마틴이 좋은 객체 지향 설계의 5가지 원칙을 정리

1. SRP : 단일 책임 원칙(Single Responsibility Principle)

2. OCP : 개방-폐쇄 원칙(Open/Closed Principle)

3. LSP : 리스코프 치환 원칙 Liskov Substitution Principle

4. ISP : 인터페이스 분리 원칙 Interface Segregation Principle

5. DIP : 의존관계 역전 원칙 Dependency Inversion Principle

## 1. SRP 단일 책임 원칙 `Single Responsibility Principle`

- 💥💥`하나의 클래스는 하나의 책임만 가져야 한다`
- 하나의 책임이라는 것은 모호하다
	- 클 수도, 작을 수도 있음
	- 문맥과 상황에 따라 다름

- `중요한 기준은 변경`!! `변경이 있을 때, 파급 효과가 적으면 단일 책임 원칙을 잘 따른 것`
- ex) UI 변경, 객체의 생성과 사용을 분리
- 계층이 나누어져 있는 것도 이를 위한 것

## 💥💥2. OCP  개방-폐쇄 원칙 `Open/Closed Principle`

- 소프트웨어 요소는 `확장에는 열려`있으나 `변경에는 닫혀`있어야 함
- `다형성을 활용` 
- 인터페이스를 구현한 새로운 클래스를 하나 만들어서 새로운 기능을 구현

아래 블로그에서 예시가 정말 친절하게 잘 정리되어 있어서 참고해서 공부해볼 수 있어서 좋았다!
https://blog.itcode.dev/posts/2021/08/14/open-closed-principle.html

(요약)

- 카드사마다 결제 처리를 진행
- 문제 - 분기로 확인하게 되면 카드사가 추가될 때마다 코드가 추가되게 됨("코드변경")

(해결)

- `역할` = "구매", `구현` = "카드" 로 접근해서 다형성으로 해결

![개방폐쇄원칙](https://github.com/hy6219/TIL/blob/main/Spring/spring_core/SOLID/%EA%B0%9C%EB%B0%A9%ED%8F%90%EC%87%84%EC%9B%90%EC%B9%99.png?raw=true)

- 모든 카드들이 공통적으로 갖는 "구매" 기능 인터페이스 Purchasable
```java
package com.solid.ocp;  
  
public interface Purchasable {  
    /**  
 * 카드사 정보 전송 및 결과 반환 함수  
  * @param price 금액  
  * @return  
  */  
  boolean send(int price);  
}
```

- Purchasable을 구현한 카드 클래스들
```java
package com.solid.ocp;  
  
public class CardA implements Purchasable{  
    @Override  
  public boolean send(int price) {  
        System.out.println(this.getClass().getSimpleName()+" "+price+"원 결제요청");  
        return true;  
    }  
}
```

```java
package com.solid.ocp;  
  
public class CardB implements Purchasable{  
    @Override  
  public boolean send(int price) {  
        System.out.println(this.getClass().getSimpleName()+" "+price+"원 결제요청");  
        return true;  
    }  
}
```

```java
package com.solid.ocp;  
  
public class CardC implements Purchasable{  
    @Override  
  public boolean send(int price) {  
        System.out.println(this.getClass().getSimpleName()+" "+price+"원 결제요청");  
        return true;  
    }  
}
```

- `실질적으로 결제 행위가 일어나도록 유발시키고, 그 결과를 반환받도록 하는 Pos`

```java
package com.solid.ocp;  
  
public class Pos {  
    /**  
 * 결제 및 결과 반환 함수  
  */  
  public boolean purchase(Purchasable purchasable, int price){  
        return purchasable.send(price);  
    }  
}
```

---
```java
package com.solid.ocp;  
  
public class Main {  
    public static void main(String[] args) {  
        Purchasable purchasable1 = new CardA();  
        Purchasable purchasable2 = new CardB();  
        Purchasable purchasable3 = new CardC();  
  
        Pos pos = new Pos();  
        System.out.println(pos.purchase(purchasable1,1200));  
        System.out.println(pos.purchase(purchasable2,2400));  
        System.out.println(pos.purchase(purchasable3,3600));  
    }  
}
```
```
CardA 1200원 결제요청
true
CardB 2400원 결제요청
true
CardC 3600원 결제요청
true

```
✅ 이렇게 되었을 때, `어떤 카드들이 추가되더라도, 실질적인 코드는 변경되지 않고` `기능 확장이 가능`한 것을 알 수 있다

👁‍🗨 대략 이렇게 이해해보고, 강의의 예제를 살펴보자

먼저 다음과 같이, 위에서처럼, MemberRepository의 자식클래스로 JdbcMemberRepository와 MemoryMemberRepository가 있고,
MemberRepository에는 MemoryMemberRepository가 있었던 상황을 생각해보자

1) MemberServiceImpl
```java
package com.example.ocp.service;  
  
import com.example.ocp.repository.MemberRepository;  
import com.example.ocp.repository.MemoryMemberRepositoryImpl;  
import org.springframework.stereotype.Service;  
  
@Service  
public class MemberServiceImpl implements MemberService {  
  
    private MemberRepository memberRepository = new MemoryMemberRepositoryImpl();  
}
```

2) MemberRepository, JdbcMemberRepository, MemoryMemberRepository

```java
package com.example.ocp.repository;  
  
import org.springframework.stereotype.Repository;  
  
@Repository  
public interface MemberRepository {  
}
```
```java
package com.example.ocp.repository;  
  
public interface JdbcMemberRepository extends MemberRepository{  
}


package com.example.ocp.repository;  
  
public class JdbcMemberRepositoryImpl implements JdbcMemberRepository{  
}
```
```java
package com.example.ocp.repository;  
  
  
public interface MemoryMemberRepository extends MemberRepository{  
}

package com.example.ocp.repository;  
  
public class MemoryMemberRepositoryImpl implements MemoryMemberRepository {  
}
```

그리고 이제 이번에는 `MemoryMemberRepository가 아닌, JdbcMemberRepository 를 주입해주는 상황을 생각`해보자

```java
package com.example.ocp.service;  
  
import com.example.ocp.repository.JdbcMemberRepositoryImpl;  
import com.example.ocp.repository.MemberRepository;  
import org.springframework.stereotype.Service;  
  
@Service  
public class MemberServiceImpl implements MemberService {  
  
    private MemberRepository memberRepository = new JdbcMemberRepositoryImpl();  
}
```

"이때, 발생할 수 있는 문제점을 알아보자"

✳ OCP 개방-폐쇄원칙 문제점

- 지금 예제에서는 MemberServiceImpl 클라이언트가 구현 클래스를 직접 선택하면서 코드가 변경되고 있음
- 이렇게 됨으로써 아래와 같은 문제가 발생하는데
```
1️⃣ 구현 객체를 변경하려면 클라이언트 코드를 변경해야 함

2️⃣ 분명히 다형성을 사용했으나, OCP 원칙을 지킬 수 없음
```
이를 "객체를 생성하고, 연관관계를 맺어주는 별도의 조립, 설정자"로 해결해볼 수 있다
▶ 이부분을 `스프링 IoC 컨테이너가 도와줄 수 있다`

## 3. LSP 리스코프 치환 원칙
Liskov Substitution Principle

- `프로그램의 객체는 프로그램의 정확성을 깨뜨리지 않으면서 하위 타입의 인스턴스로 바꿀 수 있어야` 함
- 인터페이스와 그 구현체가 있을 때, 컴파일 단계를 의미하는 것이 아니라, 그 기능의 목적에 맞게 보장을 해주어야 함

ex) 자동차의 악셀기능인데 -10 속도를 하게 하는 것은 목적에 부합
컴파일은 잘 되겠지만, 기능의 목적에 부합

## 4. ISP 인터페이스 분리 원칙
Interface Segregation Principle

- `특정 클라이언트를 위한 인터페이스 여러개가 범용 인터페이스 하나보다 낫다`

ex) 자동차 클라이언트 : 운전.정비 인터페이스로 분리
▶ 이렇게 되면, 사용자 클라이언트를 "운전자, 정비사 클라이언트"로 분리할 수 있음
▶ "운전"이나 "정비"에 대한 기능 변경시 사용자 클라이언트측을 변경하지 않아도 됨

- 인터페이스를 분리하게 됨으로써 특정 기능 변경이 다른 인터페이스에 영향을 주지 않음
- `인터페이스가 명확해지고, 대체 가능성이 높아짐`


## 5.  💥💥DIP 의존관계 역전 원칙
Dependency Inversion Principle

- 프로그래머는 "추상화에 의존해야지, 구체화에 의존하면 안된다"라는 원칙을 따르는 원칙 중 하나
- `구현 클래스에 의존하지 않고, 인터페이스에 의존`하라는 것
- **역할(Role)에 의존** 해야 한다는 것
▶ 그래야 보다 유연하게 구현체를 변경할 수 있음
▶ 시스템은 언제든지 갈아끼울 수 있도록 해야 함

 ↔ 프로그램은 MemberRepository만 알고 있으면 되고, JdbcMemberRepository나 MemoryMemberRepository는 몰라도 됨

위에서 MemberService 예시(OCP 원칙)는 MemoryMemberRepository뿐 아니라, JdbcMemberRepository도 알고 있기 때문에 "의존"하고 있다고 할 수 있음

이때, MemoryMemberRepository에서 JdbcMemberRepository로 바꿀 때 코드가 변경되게 됨
▶ DIP 위반
(추상화도 안되어 있고, 구체화에 의존하고 있음)
```java
private MemberRepository memberRepository = new MemoryMemberRepository();
```

---

🧡 정리

- 다형성만으로는 OCP, DIP를 지킬 수 없어서 뭔가 더 필요하다!
(클라이언트 코드가 변경될 수 있음)
(구현체가 없으면 위의 예제에서 npe가 터질수도..)

