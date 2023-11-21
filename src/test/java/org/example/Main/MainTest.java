package org.example.Main;
import io.restassured.response.Response;
import org.example.Base.BasePage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.example.Constants.Constants.*;

public class MainTest extends BasePage {

    private final String orgID = "61b8611a2d682538c5d32457";
    private int counter;

    @BeforeClass
    public void initialize() {
        init();
        counter = 0;
    }

    @Test(priority = 1)
    public void testingCreatingBoard() {
        Response response = createBoard("NewBoard","/boards");

        assertName(response,"NewBoard");
    }

    @Test(priority = 2)
    public void testingCreateList() {
        String BoardId = extractParameter(getItem(getBoardsPath,"id",orgID),"id");
        Response response = createItem("NewList",listIdPath,"idBoard",BoardId);
        response.then()
                .statusCode(200);
        assertName(response,"NewList");
    }

    @Test(priority = 3)
    public void testingCreateCards() {
        String BoardId = extractParameter(getItem(getBoardsPath,"id",orgID),"id");
        String listId = extractParameter(getItem(getListsPath,"id",BoardId),"id");
        Response response = createItem("NewCard " + counter,createCardPath,"idList",listId);
        assertName(response,"NewCard " + counter);

        counter++;

        response = createItem("NewCard " + counter,createCardPath,"idList",listId);
        assertName(response,"NewCard " + counter);
    }

    @Test(priority = 4)
    public void testingUpdateRandomCard() {
        String BoardId = extractParameter(getItem(getBoardsPath,"id",orgID),"id");
        String listId = extractParameter(getItem(getListsPath,"id",BoardId),"id");
        String cardId = getRandomCard(getItem(getCardsPath,"id",listId),"id");
        updateItem(cardIdPath, "id", cardId,"name","updatedCard");
    }


    @Test(priority = 5)
    public void testingDeleteCards() {
        String BoardId = extractParameter(getItem(getBoardsPath,"id",orgID),"id");
        String listId = extractParameter(getItem(getListsPath,"id",BoardId),"id");
        String cardId = extractParameter(getItem(getCardsPath,"id",listId),"id");
        deleteItem(cardIdPath,"id",cardId);

        cardId = extractParameter(getItem(getCardsPath,"id",listId),"id");
        deleteItem(cardIdPath,"id",cardId);
    }


    @Test(priority = 6)
    public void testingDeleteBoard() {
        String BoardId = extractParameter(getItem(getBoardsPath,"id",orgID),"id");
        deleteItem(boardIdPath,"id",BoardId);
    }










}



