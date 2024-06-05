# King's Saga
* Gruppeprosjekt i emnet "Innføring i systemutvikling" (INF112) ved Universitetet i Bergen
* Team *Magnum Scriptum*
* Gruppe 2
* Medlemmer: Kjell Martin Solsvik, Carl Didrik Torkildsen og  Kjetil Fantoft Alvestad
* Gitlab: https://git.app.uib.no/Kjetil.F.Alvestad/kings-saga

## Om spillet
Spillet er satt i vikingtiden. Kongen av norge er død og mellom deg og tronen står kun din bror. Du må bekjempe din bror og hans allierte for å ta tronen. Du må ta deg gjennom flere kart med farlige fiender som alle forsøker å hindre deg i å oppnå din skjebne. Kan du klare å vinne tronen og bli kongen av norge?

Spilleren beveger karakteren med tastene W, A, S og D og angriper med tasten E. 

Spillet er laget i Java med libGDX og Gradle og benytter seg av Box2D for fysikk. Spillet er ovenfra og ned og har en 2D-grafikkstil.

## Kjøring

Følg disse trinnene for å starte spillet:

1. **Installer Java Development Kit (JDK):** Spillet er skrevet i Java, så du må ha JDK installert på datamaskinen din. Du kan laste det ned fra [den offisielle Oracle-nettsiden](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html). Java 17 er testet og fungerer, men andre versjoner kan også fungere.

2. **Velg en mappe for spillet:** Bestem hvor du vil lagre spillet på datamaskinen din. Naviger til mappen ved å bruke `cd`-kommandoen i terminalen din. For eksempel, hvis du vil lagre spillet i en mappe som heter `spill`, bruker du følgende kommando:

    ```bash
    cd sti/til/spill
    ```

    Erstatt `sti/til/spill` med den faktiske stien til den valgte mappen.

3. **Klon prosjektet:** Spillets kildekode er lagret på [git.app.uib.no](https://git.app.uib.no). For å klone prosjektet kan du bruke følgende kommando. 

    ```bash
    git clone https://git.app.uib.no/Kjetil.F.Alvestad/kings-saga.git
    ```

4. **Kompiler og kjør spillet:** Spillet bruker Gradle som byggesystem. Du kan kompilere og kjøre spillet med denne kommandoen:

    ```bash
    ./gradlew desktop:run
    ```

    Denne kommandoen ber Gradle kjøre `run`-oppgaven i `desktop`-prosjektet, som vil kompilere og starte spillet. Vær oppmerksom på at spillvinduet kan være skjult bak andre vinduer, så sørg for å sjekke alle åpne vinduer.


## Kjente feil
Se [denne saken](https://git.app.uib.no/Kjetil.F.Alvestad/kings-saga/-/issues/75) for en liste over kjente feil i spillet. Rapporter gjerne nye feil.

## Kilder

### Generelle kilder fra internett
- Slash sprite fra: https://opengameart.org/content/slash-effect
- Sverd lyder fra: https://pixabay.com/users/floraphonic-38928062/
- Skrik fra: https://pixabay.com/users/alex_jauk-16800354/
- Boss roar fra: https://pixabay.com/users/voicebosch-30143949/
- Smash lyd fra: https://pixabay.com/users/lordsonny-38439655/
- Ground slam fra: https://opengameart.org/content/quake-spell
- Nøkkel fra: https://opengameart.org/content/key-sprite 
- Lås-opp lyd fra: https://pixabay.com/sound-effects/steampunk-gadget-lock-and-unlock-188053/
- Myntklirr lyd fra: https://freesound.org/people/yossirafa100/sounds/718435/ 
- Grafikk. "Tiny Swords" fra: https://pixelfrog-assets.itch.io/tiny-swords
- Grafikk. "Summer Plains" fra: https://schwarnhild.itch.io/summer-plains-tileset-and-asset-pack-32x32-pixels 

### Laget spesifikt til King's Saga
- [King's Saga Theme](assets/kingssagatheme.wav) av Carl Didrik Torkildsen (lisensiert under [CC BY-SA 4.0](https://creativecommons.org/licenses/by-sa/4.0/))
- [Boss.png](assets/Boss.png) av Liv Dornfest (lisensiert under [CC BY-SA 4.0](https://creativecommons.org/licenses/by-sa/4.0/))
- [Atmosphere](assets/Atmosphere.wav) (lisensiert under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/))
- [speedPotion.png](assets/speedPotion.png) (lisensiert under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/))
- [melee_weapon.png](assets/melee_weapon.png) (lisensiert under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/))
- [blueSword.png](assets/blueSword.png) (lisensiert under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/))
- [healthPotion.png](assets/healthPotion.png) (lisensiert under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/))
- [coin_pouch.png](assets/coin_pouch.png) (lisensiert under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/))
- [viking.png](assets/viking.png) (lisensiert under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/))
- [simpleEnemy.png](assets/simpleEnemy.png) (lisensiert under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/))
- [royalSword.png](assets/royalSword.png) (lisensiert under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/))
- [royal_chest.png](assets/royal_chest.png) (lisensiert under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/))
- [common_chest.png](assets/common_chest.png) (lisensiert under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/))
- [MainMenuVikingAnimation43format.png](assets/MainMenuVikingAnimation43format.png) (lisensiert under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/))
- [item.png](assets/item.png) (lisensiert under [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/))