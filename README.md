# Encoder-based-on-ECC

This program is an Encoder and Decoder based on the Elliptic Curve Cryptography Algorithm. 
When you run this jar Terminal like Frame will open. Type "help" in it, then you will get a list of commands availiable to you.
One of the command is "start gui". This will open the Graphical version of my project.

Explanation about the usage:
  Suppose, Alice want to send a file to Bob. Then first Alice will ask the Bob for his Public key. Bob will open the software and set some valid configuration and then Export the Public key. A file with the extension of ".key" will get generated on the path specified by the Bob. Bob will then send his Public to Alice. Bob will also need to remember the configuration he made will generating the Public Key otherwise, He won't be able to decrypt it. Bob can export his configuration also. Configuration file will be saved with ".config" extension. Alice will open the software, specify the path to the public key given by the Bob. Then she will give the path to the file that she want to encrypt in the Input File field. Then she will give the path to the output file and Enocde.
  
  Alice will then send the encrypted file to Bob. Bob will set the same configuration by importing the config file or typing manually.
  then He will give the encrypted file as input and output file as Output and then Decode.
  
  Bob will get the original file.
