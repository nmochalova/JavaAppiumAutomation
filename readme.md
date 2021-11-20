Домашняя работа Тест_1:

Сделать класс MainClass.
В классе MainClass сделать метод, который возвращает число 14 (назвать: getLocalNumber).
Сделать класс MainClassTest.
В классе MainClassTest написать тест, проверяющий, что метод getLocalNumber возвращает число 14 (назвать: testGetLocalNumber).
Не забываем в проверку добавлять понятный текст ошибки.
Результат выполнения закоммитить в репозиторий на гитхаб и прислать ссылку на коммит.

============================
Домашняя работа Тест_2:

Сделать класс MainClass (если еще нет).
Сделать в классе MainClass приватное поле класса, которое равно 20 (назвать: class_number), и публичный метод (getClassNumber), который эту переменную возвращает.
Сделать класс MainClassTest (если еще нет).
В классе MainClassTest написать тест (назвать testGetClassNumber), который проверяет, что метод getClassNumber возвращает число больше 45.
Не забываем в проверку добавлять понятный текст ошибки.
Результат выполнения закоммитить в репозиторий на гитхаб и прислать ссылку на коммит. 

============================
Домашняя работа Тест_3:

Сделать класс MainClass (если еще нет).
Сделать в классе MainClass приватное поле класса, которое равно строке “Hello, world” (назвать: class_string), и публичный метод (назвать: getClassString), который возвращает строку.
Сделать класс MainClassTest (если еще нет).
В классе MainClassTest написать тест (назвать testGetClassString), который проверяет, что метод getClassString возвращает строку, в которой есть подстрока “hello” или “Hello”, если нет ни одной из подстрок - тест падает.
Не забываем в проверку добавлять понятный текст ошибки.
Результат выполнения закоммитить в репозиторий на гитхаб и прислать ссылку на коммит.

============================
Ex1: Конфигурация инструментов

Установить и настроить все инструменты, указанные в занятии. Создать файловую структуру. Запустить тесты и убедиться в отсутствии ошибок. Результат выполнения закоммитить в репозиторий на гитхаб и прислать ссылку на коммит.
Создан класс FirstTest
Импортированы библиотеки в директорию libs и выложено приложение в директорию apks

============================
Ex2: Создание метода
Необходимо написать функцию, которая проверяет наличие ожидаемого текста у элемента. Предлагается назвать ее assertElementHasText. На вход эта функция должна принимать локатор элемент, ожидаемый текст и текст ошибки, который будет написан в случае, если элемент по этому локатору не содержит текст, который мы ожидаем.

Также, необходимо написать тест, который проверяет, что поле ввода для поиска статьи содержит текст (в разных версиях приложения это могут быть тексты "Search..." или "Search Wikipedia", правильным вариантом следует считать тот, которые есть сейчас). Очевидно, что тест должен использовать написанный ранее метод.

Создан тест testContainText и функция assertElementHasText

============================
Ex4*: Тест: проверка слов в поиске
Написать тест, который делает поиск по какому-то слову. Например, JAVA. Затем убеждается, что в каждом результате поиска есть это слово.(без прокрутки)

Создан тест testSearchWordInResultsList и метод waitForElementsPresent

============================
Ex3: Тест: отмена поиска
Написать тест, который:
Ищет какое-то слово
Убеждается, что найдено несколько статей
Отменяет поиск
Убеждается, что результат поиска пропал