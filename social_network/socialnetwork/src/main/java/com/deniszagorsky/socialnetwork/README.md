**Готовые JSON-объекты & URL для manual тестирования приложения:** 

**1. Регистрация нового пользователя:** 

URL: http://localhost:8080/socialnetwork/join/

Body:
_Пользователь №1:_
{
    "email": "donnchadh.mr@gmail.com",
    "password": "qwerty12",
    "lastName": "Zagorsky",
    "firstName": "Denis",
    "dayOfBirth": "4",
    "monthOfBirth": "2",
    "yearOfBirth": "1998",
    "sex": "MALE",
    "city": "Saint-Petersburg",
    "interests": ""
}

_Пользователь №2:_
{
    "email": "ivan@gmail.com",
    "password": "qwerty34",
    "lastName": "Ivanov",
    "firstName": "Ivan",
    "dayOfBirth": "17",
    "monthOfBirth": "9",
    "yearOfBirth": "1995",
    "sex": "MALE",
    "city": "Moscow",
    "interests": null
}

_Пользователь №3:_
{
    "email": "kate.mr@gmail.com",
    "password": "qazwsx78",
    "lastName": "Makogon",
    "firstName": "Kate",
    "dayOfBirth": "10",
    "monthOfBirth": "12",
    "yearOfBirth": "1985",
    "sex": "FEMALE",
    "city": "Moscow",
    "interests": ""
}

**2. Редактирование учетной записи пользователя:**

URL: http://localhost:8080/socialnetwork/{userId}/edit/

Body:
{
    "email": "edited.donnchadh.mr@gmail.com",
    "password": "Edited. qwegrhtyjuk",
    "firstName": "Edited. Dasha",
    "lastName": "Edited. Makogon",
    "dayOfBirth": "14",
    "monthOfBirth": "12",
    "yearOfBirth": "2000",
    "sex": "FEMALE",
    "interests": "Edited. Testing",
    "city": "Edited. Moscow",
    "instagram": "Edited. Link to Instagram",
    "website": "Edited. Link to a website"
}

**3. Get-запрос страницы конкретного пользователя:** 

URL: http://localhost:8080/socialnetwork/{userId}/

**4. Поиск пользователей, используя фильтры:** 

URL: http://localhost:8080/socialnetwork/search?fnl=Denis&lnl=Zagorsky&c=Saint-Petersburg&dob=4&mob=2&yob=1998&p=0&s=3

**5. Отправка запроса на добавление в друзья:**

URL: http://localhost:8080/socialnetwork/{friendId}/

Body:
{
    "id": "{userId}"
}

**6. Принятие запроса на добавление в друзья:**

URL: http://localhost:8080/socialnetwork/{friendId}/

Body:
{
    "id": "{userId}"
}

**7. Получение списка друзей:**

URL: http://localhost:8080/socialnetwork/{userId}/friends?p=0&s=10
 
**8. Удаление пользователя из списка друзей:**

URL: http://localhost:8080/socialnetwork/{friendId}/remove_friendship/

Body:

 
**9. Публикация поста на персональной странице:**

URL: http://localhost:8080/socialnetwork/{userId}/create_post/

Body:
{
    "body": "What a wonderful day!"
}

**10. Редактирование поста:**

URL: http://localhost:8080/socialnetwork/{userId}/post/{postId}/edit/

Body:
{
    "body": "Indeed, what a wonderful day!"
}

**11. Удаление поста:"**

URL: http://localhost:8080/socialnetwork/{userId}/post/{postId}/delete/

**12. Удаление учетной записи:**

URL: http://localhost:8080/socialnetwork/{userId}/delete/


