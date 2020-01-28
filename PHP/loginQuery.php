<?php  
error_reporting(E_ALL); 
ini_set('display_errors',1); 

include('dbcon.php');



//POST 값을 읽어온다.
$userId=isset($_POST['userId']) ? $_POST['userId'] : '';
$userPw = isset($_POST['userPw']) ? $_POST['userPw'] : '';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


if ($userId != "" ){ 

    $sql="select * from USER where userId='$userId' and userPw='$userPw'";
    $stmt = $con->prepare($sql);
    $stmt->execute();
 
    if ($stmt->rowCount() == 0){

        echo "'";
        echo $userId,", ",$userPw;
        echo "'은 찾을 수 없습니다.";
    }
	else{

   		$data = array(); 

        while($row=$stmt->fetch(PDO::FETCH_ASSOC)){

        	extract($row);

            array_push($data, 
                array(
                'userId'=>$row["userId"],
                'userName'=>$row["userName"],
                'userRank'=>$row["userRank"]
            ));
        }


        if (!$android) {
            echo "<pre>"; 
            print_r($data); 
            echo '</pre>';
        }else
        {
            header('Content-Type: application/json; charset=utf8');
            $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
            echo $json;
        }
    }
}
else {
    echo "ID를 입력하세요 ";
}

?>



<?php

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android){
?>

<html>
   <body>
   
      <form action="<?php $_PHP_SELF ?>" method="POST">
         userId: <input type = "text" name = "userId" />
         userPw: <input type = "text" name = "userPw" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
<?php
}

   
?>