# 🛵 delivery-food
- 배달 플랫폼을 기반으로 만든 토이 프로젝트 입니다.
- 고객이 주문을 하면 가게는 주문을 기반으로 음식을 준비하고 라이더가 음식을 고객에게 전달합니다.
- 회원 관리는 일반 회원, 가게 회원, 라이더 회원으로 각각 관리됩니다.

# 사용 기술
- [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle)
- [Java 11](https://docs.oracle.com/javase/11/docs/api/)
- [MySQL](https://dev.mysql.com/doc/refman/8.0/en/)
- [Mybatis](https://mybatis.org/mybatis-3/)
- [Naver cloud platform](https://docs.ncloud.com/ko/)
- [Jenkins](https://www.jenkins.io/)
- intellij IDEA

# 프로젝트 구조
![프로젝트_구조 (3)](https://user-images.githubusercontent.com/85219306/201141262-ae140be1-2e7c-4a99-8506-f8e7d99fa499.png)

# ERD
- https://www.erdcloud.com/d/jptjoTQytETu5vGps

# 브랜칭 전략
> Github Flow

![image](https://user-images.githubusercontent.com/85219306/201124161-fa06b2fd-4001-4d41-a45d-3b596f020cda.png)
- Local Branch에 수시로 커밋하고 이 내용을 원격 Branch에 수시로 Push한다.
- 피드백이나 도움이 필요할 때 혹은 자신의 Branch가 merge할 준비가 되었다면, PR을 생성하여 공유한다.
- PR 리뷰 후에는 다른 사람의 동의를 얻고 Main에 Merge한다.
- Main Branch로 Merge나 Push가 이루어지면 즉시 배포해야한다.

