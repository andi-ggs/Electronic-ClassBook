# Electronic-ClassBook

  Tema reprezinta implementarea unui Catalog electronic cu functionalitati minimale, in care se imbina
mai multe sabloane de proiectare si abordari pentru interfata grafica pentru o functionalitate
armonioasa si complexa.
  In continuare voi detalia cateva aspecte pentru o usuoara intelegere a codului si a gandirii din spatele
implementarii mele:
  • Interfata TupleInterface -> folosita pentru a accesta datele din clasa interna, private
Tuple, clasa care implementeaza aceasta metoda. In continuare, atat in clasa ScoreVisitor
cat si in clasa Test, am folosit aceasta interfata ca argument.
  • ScoreVisitor -> am folosit in plus 4 metode, 2 pentru introducerea in dictionarele
specifice notelor din examen si din partial, si 2 pentru obtinerea acestora (metodele get).
  • Interfata Strategy -> am introdus antentul unei metode commune, pe care o voi folosi
in clasele BestExamScore, BestPartialScore, BestTotalScore, iar aceasta interfata este
implemenetata de clasa Course si prin intermediul metodei getBestStudent, afiseaza, in
functie de strategia aleasa de profesorul titular, studentul dorit.
  • Interfata Subject -> implementata de clasa Catalog care ii mosteneste si, mai departe,
ii implementeaza metodele, intrucat catalogul reprezinta punctul de interes pentru
observatori.
  • Interfata Observer -> implementata de clasa Parent care ii mosteneste si, mai departe,
implementeaza metodele, deoarece parintii sunt aceia care urmaresc parcursul academic
al copiilor lor (studentii).
  • StudentPage, TeacherPage, ParentPage - > paginile pentru student,
professor/asistent si, respective, parinti, care respecta (din punctul meu de vedere)
cerintele enuntului si au avut un grad de dificultate mediu spre scazut, sunt implementate
aceleasi metode invatate si exersate la laborator.
  • LoginPage -> exercitiul bonus, pe care l-am gandit astfel:
o Un Student, va folosi numele sau (prenume si nume) pentru Username, si “student”
pe post de parola. Astfel, programul va citi din campul Password tipul fiecarui
utilizator (student/teacher/parent/asistent), va prelua numele si va afisa pagina
specifica fiecaruia.
  o Analog pentru profesori, asistenti si parinti.
  • Studentlist (TreeSet), parents (HashSet), assistantslist (Arraylist), teacherlist
(Arraylist) -> folosite pentru a stoca lista cu toti studentii, parintii, asistentii etc, fara
duplicate, pentru a fi mai usor accesibile obiectele de tipurile respective.
  Restul claselor, sunt, din punctul meu de vedere, conforme cerintei temei, iar prin intermediul
comentariilor pe cod, parcurgerea lor ar trebui sa fie usurata.
