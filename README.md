# Snake Hra

Jednoduchá implementace klasické hry Snake (Had) v jazyce Java pomocí knihovny Swing. Hra byla vytvořena jako první programátorský projekt na motivy legendárního dílu z Výměny manželek ("Mačkáš mi hada, debile!").

## Popis hry
Cílem hry je ovládat hada, sbírat jablka a dosáhnout co nejvyššího skóre. S každým snězeným jablkem se had prodlouží a skóre se zvýší. Hra končí, pokud had narazí do stěny nebo do vlastního těla.

## Funkce
- **Pohyb**: Ovládání hada pomocí šipek na klávesnici.
- **Skóre**: Počítadlo snězených jablek zobrazené v horní části obrazovky.
- **Restart**: Možnost restartovat hru tlačítkem "Hrát znovu" po prohře.
- **Grafika**: Jednoduché 2D prostředí vykreslované pomocí Java Swing.

## Technologie
- **Java 19**: Hlavní programovací jazyk.
- **Swing**: Knihovna pro grafické uživatelské rozhraní (GUI).
- **Maven**: Nástroj pro správu závislostí a sestavování projektu.

## Požadavky
- Java Development Kit (JDK) 19 nebo novější.
- Maven (pro sestavení z příkazové řádky).

## Instalace a spuštění

### 1. Klonování repozitáře
```bash
git clone <URL_repozitare>
cd Snake
```

### 2. Sestavení projektu (Maven)
Pokud máte nainstalovaný Maven, můžete projekt sestavit příkazem:
```bash
mvn clean install
```
Tento příkaz vytvoří spustitelný JAR soubor ve složce `target/`.

### 3. Spuštění hry
Po úspěšném sestavení můžete hru spustit tímto příkazem:
```bash
java -jar target/JavaSnakeGame-1.0-SNAPSHOT.jar
```

Případně můžete projekt otevřít v IDE (IntelliJ IDEA, Eclipse, NetBeans) a spustit třídu `com.mycompany.javasnakegame.JavaSnakeGame` přímo.

## Ovládání
- **Šipka nahoru (↑)**: Pohyb nahoru
- **Šipka dolů (↓)**: Pohyb dolů
- **Šipka doleva (←)**: Pohyb doleva
- **Šipka doprava (→)**: Pohyb doprava

## Autor
Vytvořil [hmac1].
