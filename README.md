# CoworkingSpace
Dies ist das Projekt aus dem Modul 223
Das Programm stellt das Backend einer App dar, die es Benutzern erlaubt, an einem bestimmten Datum ein Termin im Büro zu buchen. Dabei gibt es eine Registration und ein Login, damit einzelne User leicht identifiziert werden können. Administratoren haben mehr Rechte, um die Coworking Space zu verwalten.

Um das Projekt aufzusetzen, muss eine Datenbank erstellt werden. Wenn sie nicht genau db_coworking_space heisst, muss dieser Name unter application.properties verändert werden.

Das Programm kann leicht in einer Entwicklungsumgebung geöffnet werden und dann durch den Startknopf hochgefahren werden.

Testdaten werden in der Hauptklasse RestApplication.java erstellt. Diese können modifiziert werden, sodass diese beim Aufstart geladen werden. Diese Testdaten werden bei jedem neuen Aufstart resetted.
