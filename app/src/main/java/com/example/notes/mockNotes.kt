package com.example.notes

import java.util.UUID

data class Note(
    val id: UUID,
    val title: String,
    val content: String
)

val mockNotes = listOf(
    Note(
        id = UUID.randomUUID(),
        title = "Мысли о замечательном",
        content = "А ведь если и правда сильно задуматься"
    ),
    Note(
        id = UUID.randomUUID(),
        title = "Сериализации JSON в Go",
        content = "В стандартном пакете encoding/json присутствуют механизмы сериализации marshaling и десериализации unmarshaling JSON.\n" +
                "\n" +
                "Пример:\n" +
                "\n" +
                "data, err := json.Marshal(yourVar)\n" +
                "Метод Marshal() принимает переменную yourVar любого типа, которую нужно сериализовать в JSON, и возвращает два значения: сериализованные данные в виде байтового массива ([]byte) и ошибку (error), если таковая возникает.\n" +
                "\n" +
                "Пример:\n" +
                "\n" +
                "data, err := json.Marshal(yourVar)\n" +
                "if err != nil {\n" +
                "  return err\n" +
                "}\n" +
                "\n" +
                "// можем использовать `data` без опасений\n" +
                "Ошибка возвращается только в случаях, когда невозможно корректно сериализовать данные. К примеру, если ваш объект содержит следующие типы, сериализация завершится с ошибкой:\n" +
                "\n" +
                "Каналы\n" +
                "\n" +
                "Комплексные числа\n" +
                "\n" +
                "Функции\n" +
                "\n" +
                "ch := make(chan struct{})\n" +
                "_, err := json.Marshal(ch) // returns error *json.UnsupportedTypeError\n" +
                "\n" +
                "compl := complex(10, 11)\n" +
                "_, err = json.Marshal(compl) // returns error *json.UnsupportedTypeError\n" +
                "\n" +
                "fn := func() {}\n" +
                "_, err = json.Marshal(fn) // returns error *json.UnsupportedTypeError"
    ),
    Note(
        id = UUID.randomUUID(),
        title = "Какие поля структуры видны для json пакета?",
        content = "Одна из распространенных ошибок — ожидание, что пакет json сможет обработать как публичные (public), так и приватные (private) поля структур. На самом деле, пакет json сериализует только публичные поля, имена которых начинаются с заглавной буквы. Приватные поля, начинающиеся с маленькой буквы, будут игнорироваться при сериализации, что важно учитывать при проектировании структур данных для работы с JSON."
    ),
    Note(
        id = UUID.randomUUID(),
        title = "Десериализация JSON в Go",
        content = "Для десериализации JSON-данных в Go используется метод json.Unmarshal(), позволяющий преобразовать данные из JSON обратно в структуру Go.\n" +
                "\n" +
                "myVal := MyVal{}\n" +
                "byte := `{\"some\":\"json\"}`\n" +
                "err := json.Unmarhal(byte, &myVal)\n" +
                "При десериализации могут возникнуть следующие ошибки:\n" +
                "\n" +
                "Если данные невозможно десериализовать из-за несоответствия типов или других проблем с форматом JSON.\n" +
                "\n" +
                "Если в качестве второго аргумента передан не указатель, то есть изменения не могут быть применены к переданной переменной.\n" +
                "\n" +
                "Если второй аргумент — nil, что означает отсутствие целевого объекта для десериализации данных."
    ),
    Note(
        id = UUID.randomUUID(),
        title = "Обработка имен полей.",
        content = "При десериализации json.Unmarshal ищет совпадения имен полей в структуре с ключами в JSON. Если точное совпадение имени не найдено, поиск повторяется, игнорируя регистр букв. В случае, если в JSON отсутствует поле, соответствующее полю структуры, значение этого поля останется неизменным (то есть будет сохранено его начальное значение)."
    ),
    Note(
        id = UUID.randomUUID(),
        title = "Структурные теги в Go для работы с JSON",
        content = "Мы можем использовать структурные теги для управления именами полей или изменения имен при декодировании.\n" +
                "\n" +
                "Предположим у нас есть структура с двумя полями. Когда мы сериализуем эти поля, оба поля будут заглавными. Часто нам это и требуется.\n" +
                "\n" +
                "type User struct {\n" +
                "  ID string\n" +
                "  Username string\n" +
                "}\n" +
                "\n" +
                "// вывод будет примерно таким\n" +
                "{\"ID\":\"some-id\",\"Username\":\"admin\"}\n" +
                "Для модификации этого поведения мы можем воспользоваться структурными тегами. После определения типа поля добавляется текст, где первым словом идет имя поля в формате JSON, далее следует разделитель :, а после — значение тега в двойных кавычках, как показано в примере ниже.\n" +
                "\n" +
                "type User struct {\n" +
                "  ID       string `json:\"id\"`     // Сериализуется как \"id\"\n" +
                "  Username string `json:\"user\"`   // Сериализуется как \"user\"\n" +
                "}\n" +
                "\n" +
                "\n" +
                "u := User{ID: \"some-id\", Username: \"admin\"}\n" +
                "\n" +
                "// вывод будет примерно таким\n" +
                "{\"id\":\"some-id\",\"user\":\"admin\"}\n" +
                "В примере мы переименовали оба поля. Имя поля может быть любым валидным json ключом.\n" +
                "\n" +
                "Опция omitempty позволяет исключать поля из сериализованного JSON, если их значение равно нулю (или эквивалентно нулю для данного типа).\n" +
                "\n" +
                "type User struct {\n" +
                "  ID       string `json:\"id\"`\n" +
                "  Username string `json:\"user\"`\n" +
                "  Age      int    `json:\"age,omitempty\"` // Пропускается, если значение 0\n" +
                "}\n" +
                "Если мы не хотим менять имя поля мы можем пропустить его, но не забывайте использовать запятую в случае использования omitempty\n" +
                "\n" +
                "type User struct {\n" +
                "  ID string `json:\"id\"`\n" +
                "  Username string `json:\"user\"`\n" +
                "  Age string `json:\",omitempty\"` // обратите внимание на запятую\n" +
                "}\n" +
                "Если мы хотим игнорировать публичное поле, мы должны использовать тэг -\n" +
                "\n" +
                "type User struct {\n" +
                "  ID       string `json:\"id\"`\n" +
                "  Username string `json:\"user\"`\n" +
                "  Age      int    `json:\"-\"` // Полностью игнорируется при сериализации\n" +
                "}\n" +
                "\n" +
                "\n" +
                "u := User{ID: \"some-id\", Username: \"admin\", Age: 18}\n" +
                "\n" +
                "// поле age отсутствует:\n" +
                "{\"id\":\"some-id\",\"user\":\"admin\"}"
    ),
    Note(
        id = UUID.randomUUID(),
        title = "Важно помнить:",
        content = "Структурные теги обрабатываются во время выполнения программы (runtime).\n" +
                "\n" +
                "Компилятор не выдаст ошибки за неправильно сформированные структурные теги, что означает необходимость внимательной проверки синтаксиса тегов.\n" +
                "\n" +
                "Опция omitempty и тег - предоставляют гибкость в управлении выводом JSON, позволяя создавать более чистый и оптимизированный вывод."
    ),Note(
        id = UUID.randomUUID(),
        title = "Использование json.Encoder и json.Decoder в Go",
        content = "В пакете encoding/json Go также представлены json.Decoder и json.Encoder, которые дополняют функциональность json.Marshal() и json.Unmarshal().\n" +
                "\n" +
                "Основное различие между этими инструментами заключается в том, что json.Encoder и json.Decoder предназначены для работы с потоками данных и напрямую взаимодействуют с объектами, поддерживающими интерфейс io.Writer для записи и io.Reader для чтения, соответственно. В отличие от этого, json.Marshal() и json.Unmarshal() оперируют массивами байтов.\n" +
                "\n" +
                "Это делает json.Decoder и json.Encoder предпочтительными для использования в ситуациях, когда требуется обрабатывать данные на лету, еще до их полного получения или когда работа ведется с потоковыми данными.\n" +
                "\n" +
                "Давайте, для примера, реализуем чтение тела запроса. Будем использовать и Unmarshal и Decoder.\n" +
                "\n" +
                "req := CreateOrderRequest{}\n" +
                "if err := json.Decoder(r.Body).Decode(&req); err != nil {\n" +
                "  // обработка ошибки\n" +
                "}\n" +
                "\n" +
                "// req готов к использованию \n" +
                "Теперь используем Unmarshal для сравнения читабельности кода.\n" +
                "\n" +
                "req := CreateOrderRequest{}\n" +
                "body, err := io.ReadAll(r.Body)\n" +
                "if err != nil {\n" +
                "  // обработка ошибки\n" +
                "}\n" +
                "\n" +
                "if err = json.Unmarshal(body, &req); err != nil {\n" +
                "  // обработка ошибки\n" +
                "}\n" +
                "Еще одно различие, которое может подсказать нам, какой инструмент выбрать, заключается в том, что мы можем многократно применять json.Decoder и json.Encoder к одному и тому же io.Reader и io.Writer. Это означает, что если поток данных, передаваемый декодеру, содержит несколько объектов JSON, мы можем создать декодер один раз, но вызывать метод Decode() многократно.\n" +
                "\n" +
                "req := CreateOrderRequest{}\n" +
                "decoder := json.Decoder(r.Body)\n" +
                "\n" +
                "for err := decoder.Decode(&req); err != nil {\n" +
                "\t// обработка одного запроса\n" +
                "}\n" +
                "В случае, когда у нас есть данные в формате []byte и мы предпочитаем использовать json.Decoder для их обработки, нам может помочь использование пакета bytes и его компонента Buffer. Buffer позволяет легко преобразовать наш слайс байтов в поток (io.Reader), который необходим для работы с json.Decoder.\n" +
                "\n" +
                "var body []byte\n" +
                "buf := bytes.NewBuffer(body)\n" +
                "\n" +
                "decoder := json.Decoder(buf)\n" +
                "for err := decoder.Decode(&req); err != nil {\n" +
                "\t// обработка одного запроса\n" +
                "}"
    ),
    Note(
        id = UUID.randomUUID(),
        title = "Тест",
        content = "Я написал простые тесты, чтобы сравнить оба подхода.\n" +
                "\n" +
                "package jsons\n" +
                "\n" +
                "import (\n" +
                "\t\"bytes\"\n" +
                "\t\"encoding/json\"\n" +
                "\t\"io\"\n" +
                "\t\"testing\"\n" +
                ")\n" +
                "\n" +
                "var j = []byte(`{\"user\":\"Johny Bravo\",\"items\":[{\"id\":\"4983264583302173928\",\"qty\": 5}]}`)\n" +
                "var createRequest = CreateOrderRequest{\n" +
                "\tUser: \"Johny Bravo\",\n" +
                "\tItems: []OrderItem{\n" +
                "\t\t{ID: \"4983264583302173928\", Qty: 5},\n" +
                "\t},\n" +
                "}\n" +
                "var err error\n" +
                "var body []byte\n" +
                "\n" +
                "// OrderItem представляет элемент заказа.\n" +
                "type OrderItem struct {\n" +
                "\tID  string `json:\"id\"` // Идентификатор элемента\n" +
                "\tQty int    `json:\"qty\"` // Количество\n" +
                "}\n" +
                "\n" +
                "// CreateOrderRequest описывает запрос на создание заказа.\n" +
                "type CreateOrderRequest struct {\n" +
                "\tUser  string      `json:\"user\"` // Пользователь, совершающий заказ\n" +
                "\tItems []OrderItem `json:\"items\"` // Список элементов заказа\n" +
                "}\n" +
                "\n" +
                "// BenchmarkJsonUnmarshal измеряет производительность функции json.Unmarshal.\n" +
                "func BenchmarkJsonUnmarshal(b *testing.B) {\n" +
                "\tb.ReportAllocs() // Отчет о выделениях памяти\n" +
                "\treq := CreateOrderRequest{}\n" +
                "\tb.ResetTimer() // Сброс таймера для чистого измерения\n" +
                "\n" +
                "\tfor i := 0; i < b.N; i++ {\n" +
                "\t\terr = json.Unmarshal(j, &req) // Десериализация JSON в структуру\n" +
                "\t}\n" +
                "}\n" +
                "\n" +
                "// BenchmarkJsonDecoder измеряет производительность использования json.Decoder.\n" +
                "func BenchmarkJsonDecoder(b *testing.B) {\n" +
                "\tb.ReportAllocs()\n" +
                "\treq := CreateOrderRequest{}\n" +
                "\tb.ResetTimer()\n" +
                "\n" +
                "\tfor i := 0; i < b.N; i++ {\n" +
                "\t\tb.StopTimer() // Остановка таймера на время подготовки\n" +
                "\t\tbuff := bytes.NewBuffer(j) // Создание буфера для чтения\n" +
                "\t\tb.StartTimer() // Возобновление измерения времени\n" +
                "\n" +
                "\t\tdecoder := json.NewDecoder(buff) // Создание декодера\n" +
                "\t\terr = decoder.Decode(&req) // Декодирование JSON в структуру\n" +
                "\t}\n" +
                "}\n" +
                "\n" +
                "// BenchmarkJsonMarshal измеряет производительность функции json.Marshal.\n" +
                "func BenchmarkJsonMarshal(b *testing.B) {\n" +
                "\tb.ReportAllocs()\n" +
                "\tfor i := 0; i < b.N; i++ {\n" +
                "\t\tbody, err = json.Marshal(createRequest) // Сериализация структуры в JSON\n" +
                "\t}\n" +
                "}\n" +
                "\n" +
                "// BenchmarkJsonEncoder измеряет производительность использования json.Encoder.\n" +
                "func BenchmarkJsonEncoder(b *testing.B) {\n" +
                "\tb.ReportAllocs()\n" +
                "\tfor i := 0; i < b.N; i++ {\n" +
                "\t\tencoder := json.NewEncoder(io.Discard) // Создание энкодера, вывод в /dev/null\n" +
                "\t\terr = encoder.Encode(createRequest) // Кодирование структуры в JSON\n" +
                "\t}\n" +
                "}\n" +
                "После запуска (по крайней мере на моих данных) json.Unmarshal() в три раза быстрее чем json.Decoder.\n" +
                "С другой стороны json.Marshal()и json.Encoder\n" +
                "показывают схожие результаты.\n" +
                "\n" +
                "BenchmarkJsonUnmarshal-10        1345796               894.4 ns/op           336 B/op          9 allocs/op\n" +
                "BenchmarkJsonDecoder-10           522276              2226 ns/op            1080 B/op         13 allocs/op\n" +
                "BenchmarkJsonMarshal-10          6257662               193.1 ns/op           128 B/op          2 allocs/op\n" +
                "BenchmarkJsonEncoder-10          6867033               174.9 ns/op            48 B/op          1 allocs/op\n" +
                "Я призываю вас не полагаться на мои результаты, а провести подобные тесты на своих приложениях."
    ),
    Note(
        id = UUID.randomUUID(),
        title = "Форматирование JSON",
        content = "Вы, возможно, заметили, что JSON-файл, полученный с помощью функций json.Marshal или json.Encoder, представляет собой компактную строку. Это означает, что он не содержит дополнительных пробелов, которые могли бы сделать его более читаемым для человека. В результате такой JSON идеален для передачи данных, но может быть неудобен для восприятия.\n" +
                "\n" +
                "В качестве решения этой проблемы можно использовать функцию json.MarshalIndent, которая позволяет отформатировать вывод JSON, делая его более удобным для чтения.\n" +
                "\n" +
                "Пример использования json.MarshalIndent:\n" +
                "\n" +
                "data := map[string]int{\n" +
                "\t\t\"a\": 1,\n" +
                "\t\t\"b\": 2,\n" +
                "\t}\n" +
                "\n" +
                "\tb, err := json.MarshalIndent(data, \"<префикс>\", \"<отступ>\")\n" +
                "\tif err != nil {\n" +
                "\t\tlog.Fatal(err)\n" +
                "\t}\n" +
                "\n" +
                "\tfmt.Println(string(b))\n" +
                "\t\n" +
                "\t// вывод\n" +
                "\t{\n" +
                "<префикс><отступ>\"a\": 1,\n" +
                "<префикс><отступ>\"b\": 2\n" +
                "<префикс>}"
    ),
    Note(
        id = UUID.randomUUID(),
        title = "MarshalJSON and UnmarshalJSON",
        content = "Чтобы задать для типа собственные правила сериализации, нужно добавить методы MarshalJSON() и UnmarshalJSON():\n" +
                "\n" +
                "type Marshaler interface {\n" +
                "\tMarshalJSON() ([]byte, error)\n" +
                "}\n" +
                "\n" +
                "type Unmarshaler interface {\n" +
                "\tUnmarshalJSON([]byte) error\n" +
                "}"
    ),
    Note(
        id = UUID.randomUUID(),
        title = "UnmarshalJSON example",
        content = "Рассмотрим на примере. Предположим, мы получаем JSON с характеристиками компьютера, где размеры оперативной памяти и диска указаны в байтах, и нам нужно представить эти данные в более читаемом виде.\n" +
                "\n" +
                "{\n" +
                "\t\"cpu\": \"Intel Core i5\",\n" +
                "\t\"operatingSystem\": \"Windows 11\",\n" +
                "\t\"memory\": 17179869184,\n" +
                "\t\"storage\": 274877906944\n" +
                "}\n" +
                "В таком формате данные сложно воспринимать. Подготовим структуру для их хранения:\n" +
                "\n" +
                "type PC struct {\n" +
                "\tCPU string\n" +
                "\tOperatingSystem string\n" +
                "\tMemory string\n" +
                "\tStorage string\n" +
                "}\n" +
                "Для решения задачи потребуется создать новый тип. Мы реализуем для него метод UnmarshalJSON, который позволит кастомизировать процесс десериализации JSON в этот тип.\n" +
                "\n" +
                "type Memory string // Определяем тип Memory как строку\n" +
                "\n" +
                "// UnmarshalJSON - специализированный метод для десериализации данных JSON в тип Memory.\n" +
                "// Этот метод реализует интерфейс json.Unmarshaler, позволяя кастомизировать процесс десериализации.\n" +
                "func (m *Memory) UnmarshalJSON(b []byte) error {\n" +
                "    // Преобразуем входящие байты в строку и пытаемся конвертировать в число.\n" +
                "    // Это число предполагается быть размером памяти в байтах.\n" +
                "    size, err := strconv.Atoi(string(b))\n" +
                "    if err != nil {\n" +
                "        return err // Возвращаем ошибку, если конвертация не удалась\n" +
                "    }\n" +
                "\n" +
                "    // Перебираем предопределенные размеры памяти и соответствующие суффиксы (например, MB, GB).\n" +
                "    for i, d := range memorySizes {\n" +
                "        if size > d {\n" +
                "            // Если размер больше текущего делителя, форматируем вывод используя этот делитель\n" +
                "            // и соответствующий суффикс, чтобы получить читаемое представление объема памяти.\n" +
                "            *m = Memory(fmt.Sprintf(\"%d %s\", size/d, sizeSuffixes[i]))\n" +
                "            return nil // Завершаем функцию успешно\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    // Если размер меньше любого из предопределенных делителей, выводим его как количество байт.\n" +
                "    *m = Memory(fmt.Sprintf(\"%d b\", size))\n" +
                "    return nil\n" +
                "}\n" +
                "Этот метод позволяет десериализовать численное значение размера памяти из JSON в удобочитаемый формат с использованием соответствующих единиц измерения (например, GB, MB).\n" +
                "Полный код"
    )
)