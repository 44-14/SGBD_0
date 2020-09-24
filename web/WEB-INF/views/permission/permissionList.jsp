<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 20-08-20
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<!-- Source : https://stackoverflow.com/questions/5967564/form-inside-a-table
     Plutot que faire un formulaire pour chaque button editer - supprimer - afficher , on fait un gros formulaire qui englobe la table
     Chaque button va submit le même formulaire mais le name du button varie et c'est le contenu de ce name qu'on checkera dans le switch dans la doPost du servlet
     pour savoir ce qu'il faut réaliser
-->


<div class="errorMessage">

</div>

<div class="pageInfo">
    <h2 class="pageInfo"> Liste des permissions </h2>
</div>


    <form  method="post"  action="${pageContext.request.contextPath}/permission" >
        <!-- Table contenant listant les permissions avec infos sommaires -->
        <table class="permission table  table-dark table-hover">  <!-- autre value de attribut class : table-striped -->

                <thead>
                    <tr>
                        <th> ID </th>
                        <th> Label</th>
                        <th> Code</th>
                        <th>  </th>
                        <th>  </th>
                        <th> Action</th>
                        <th> </th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach items="${requestScope.myPermissionListRequestKey}" var="permission">
                        <tr>
                            <td>${permission.id}</td>
                            <td><c:out value="${permission.abbreviation}" /></td>
                            <td><c:out value="${permission.label}" /></td>

                            <td><input type="hidden" id="idPermissionForm" name="idPermissionFromForm"  value="${permission.id}"> </td>
                            <!-- Pour chaque button :
                            retirer redAlert ou greenAlert pour eviter le clignotement et remplacer -danger ou -success par -primary quand tout sera implémenté -->
                            <td><button class="myFormActionButton btn btn-sm btn-outline-success greenAlert"
                                        type='submit' name="actionFromForm" value="readOne" > Afficher </button> </td>
                            <td><button class="myFormActionButton btn btn-sm btn-outline-danger redAlert"
                                        type='submit' name="actionFromForm" value="editOne" > Editer </button> </td>
                            <td><button class="myFormActionButton btn btn-sm btn-outline-danger redAlert"
                                        type='submit' name="actionFromForm" value="deleteOne" > Supprimer </button> </td>

                        </tr>
                     </c:forEach>
                </tbody>
        </table>
    </form>








