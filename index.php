<?php

phpinfo();


function pdf2png($PDF,$filePath){
   if(!extension_loaded('imagick')){
   		echo "load extension failed\n";
       return false;
   }
   if(!file_exists($PDF)){
       return false;
   }
   $IM = new imagick();
   $IM->setResolution(120,120);
   $IM->setCompressionQuality(100);
   $IM->readImage($PDF);
   $canvas = new Imagick();
   $canvas->newImage($IM->getImageWidth(), $IM->getImageHeight() * $IM->getNumberImages(), 'white');
   $canvas->setImageFormat('png');
   $index = 0 ;
   $x = 0;
   $y = 0;
   foreach ($IM as $Key => $Var){
       $Var->setImageFormat('png');
       $canvas->compositeImage($Var, $Var->getImageCompose(), $x, $y);
       $y += $Var->getImageHeight();
       /*
       $Filename = $Path.'/'.$index++.'.png';
       if($Var->writeImage($Filename) == true){
           $Return[] = $Filename;
       }
       */
   }
   return $canvas->writeImage($filePath);
}

$pdfPath = "/tmp/test/1.pdf";
$destPath = "/tmp/test/1.png";

if(false == pdf2png($pdfPath, $destPath))
	echo "pdf2 png failed";
else
	echo "pdf2 png success";

?>
