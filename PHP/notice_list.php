<?php  
error_reporting(E_ALL); 
ini_set('display_errors',1); 

include('dbcon.php');



$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


if (true){ 

    $sql="select * from NOTICE";
    $stmt = $con->prepare($sql);
    $stmt->execute();
 
    if ($stmt->rowCount() == 0){


        echo "데이터가 없습니다.";
    }
	else{

   		$data = array(); 

        while($row=$stmt->fetch(PDO::FETCH_ASSOC)){

        	extract($row);

            array_push($data, 
                array('title'=>$row["title"],
                'date'=>$row["date"],
                'writer'=>$row["writer"]
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

}

?>



<?php

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android){
?>

<html>
   <body>
   
      <form action="<?php $_PHP_SELF ?>" method="POST">
         나라: <input type = "text" name = "country" />
         이름: <input type = "text" name = "name" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
<?php
}

   
?>