citewidget
==========

This is Android Widget application that displays famous quotes in several languages. Spanish, French, German will be the first ones to implement. Will likely add English and Russian for completeness. May add more. The interface imitates excellent 'Cita del Dia' app that displays spanish quotes only.

Implemented features so far:
1. Interface is more or less of what is planned. Looks nice; click on quote shows next language.
2. Offline resource pool for some number of quotes for cases when web update is not available.

It is very much wip yet. Features required for more or less functional release (not in priority order):
1. Daily updates from the web.
2. Translate static resource XML to sqlite. update on offline xml version change.
3. Save state in prefs. putvar(), getvar().
4. Updates implemented as a service.
5. Language selection prefs dialog.
6. Preferences dialog launched by flag-image button.
7. Actual contents of the offline resource pool.
8. Help / translation
9. Populate on Google Play.
