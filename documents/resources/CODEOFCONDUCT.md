# Code of Conduct

## 1. "final"
Variablen sollen grundsätzlich als final deklariert werden, wenn es nicht eine ausdrücklichen Grund gibt, warum dies nicht der Fall sein sollte.

    ``` java
    @Nonnull
    public Person find(final long id) {
        return personRepository.getReferenceById(id);
    }
    ```

## 2. "@Nonnull"
Return Werte und Parameter sollen grundsätzlich mit der Annotation `@Nonnull` deklariert werden, wenn es nicht eine ausdrücklichen Grund gibt, warum dies nicht der Fall sein sollte.

    ``` java
    @Nonnull
    public Person find(final long id) {
        return personRepository.getReferenceById(id);
    }
    ```
## 3. "Branch Namen"
Branchen sollen grundsätzlich mit dem Prefix "feature-" beginnen und dannach die Nummer vom entsprechenden Product Work Item (PWI) enthalten. Die Feature Branches sollen grundsätzlich im Verzeichnis "feature/" liegen.

    ``` java
    feature/feature-3Pipeline
    ```
## 4. "Commit Nachrichten"
Commit Nachrichten sollen grundsätzlich in der Form "PWI-Nummer: Commit-Nachricht" sein.

    ``` java
    PWI-3: Add pipeline
    ```

## 5. "Pull Request"
Pull Requests sollen grundsätzlich mit dem Prefix "PWI-" beginnen und dannach die Nummer vom entsprechenden Product Work Item (PWI) enthalten.

    ``` java
    PWI-3: Add pipeline
    ```