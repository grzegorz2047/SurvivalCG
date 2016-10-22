# SurvivalCG![alt tag](https://travis-ci.org/grzegorz2047/SurvivalCG.svg?branch=master)

Plugin pod serwery Minecraft używające silnik bukkit, spigot i inne forki bukkita kompilowany pod api spigota 1.8.
Prosty system rankingowy + teamy itd. Plugin robiony dla serwerowni CraftGames.pl
Ogólnie to inspirowałem się poprzednim pluginem OpenGuild, który stał się małym potworkiem.
Dlatego w tym pluginie brałem tylko te elementy pod uwagę, które zostały najlepiej napisane.
Udostępniam mój progres, aby ewentualnie wysluchać co można napisać lepiej badź latwiej no i dla osób ciekawych moich najnowszych projektów.
Co do pull requestów to na poczatku nie bede akceptować, jeżeli takowe beda, gdyż plugin jest wciąż robiony.

Aby skompilować plugin możesz użyć IntelliJ IDEA i zrobić checkout z githuba.
Następnie potrzebujesz dołączyć bibliotekę HikariCP i bukkit/spigot do kompilacji w IDE.
Przy eksporcie pluginu potrzebujesz HikariCP-2.4.3.jar, slf4j-api-1.7.13.jar, Protocollib 3.6.0 oraz bukkit-api/spigot-api 1.7.10/1.8 albo nowsze i dac je jako extracted directory.
Nie chcialo mi sie kombinowac z gradlem, więc trochę trzeba pokombinować :)

Aby przetestować plugin wystarczy wejść na serwer: mc.craftgames.pl i znaleźć przedmiot o nazwie przetrwanie i kliknąć.

Jakiekolwiek dotacje mile widziane :) (paypal): http://bit.ly/1IA5PaX

PS. Plugin nie generuje tabelek w mysqlu, więc trzeba to zrobić ręcznie: https://drive.google.com/drive/folders/0Bw1f3oHYdM2nYXdwUGlwLWtCR0U

--------------------------------------------------------------------------------
It's minecraft plugin for bukkit, spigot and other forks of bukkit servers.
Simple ranking system using scoreboards + teams etc. Plugin made for CraftGames.pl
I share my progress to receive hints from better java programmers who can look at the code and say what can be done better or easier.
I don't accept pull request for now because plugin is still under development.

To compile a plugin you can user IntelliJ IDEA and checkout project from github.
Next you need HikariCP library to be able to compile in IDE.
To export you need extracted directory of HikariCP-2.4.3.jar and slf4j-api-1.7.13.jar or probably newer,
I was to lazy to play with Gradle so you need some patient with compiling :)

Feel free to donate any amount of money :) (paypal): http://bit.ly/1IA5PaX

PS. Plugin doesn't generate mysql tables in database so you have to create it on you own: https://drive.google.com/drive/folders/0Bw1f3oHYdM2nYXdwUGlwLWtCR0U
