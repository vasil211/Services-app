# ServiceApp
 console app for services 


https://github.com/vasil211/Services-app


For the program to run correctly:
1.You need to run the sql script
2.Change the username and password in src\app\util\Database for the connection
3.Import/Include the mysql-connector-java-8.0.27.jar in project Modules: File->Project Structure->Modules

demo users:
Admin: 
    username: admin 
    password: Admin123123

Moderator:
    username: moderator
    password: Admin123123

Service Provider:
    username: serviceProvider
    password: Admin123123

User:
    username: userF
    password: Admin123123
    
    username: userSec   
    password: Admin123123

And you can always register new user from the register form.


This program, is a console app, for posts for services. 
The 4 roles as users are - Admin; Moderator; Service Provider; User;
All the passwords are encrypted. Personal messages cannot be seen by Moderators or Admins.
There cannot be two users with the same username or email.

As you start the program, you are presented with a menu from which you can register, login or explore posts.

As you register as user, you can do the following actions:
1 - you can browse all the posts, you can search them by name, or to look through categories.
2 - you can message the service provider directly from the post, or create appointment with the provider.
3 - when you create appointment, you can look the progress for all your appointments - pending, accepted, 
    finished and declined.
4 - you can look through all your messages with different providers, open specific chart and send messages.
5 - you can manage your personal data and information.
6 - you can apply to become Service Provider. You have to wait a moderator to review your application and accept it, 
    or if its denied you will see message why is denied, and can apply again.

As Service Provider:
1 - you can browse through the appointments made for you, you can accept and finish or decline. If you decline, 
    you will have to give an explanation.
2 - you can browse through the messages sent to you, you can open specific chart and send messages.
3 - you can manage your posts, update or delete them.
4 - you can create new posts.
5 - you can manage your personal data and information.
6 - you can switch back to regular user menu, from witch you can behave as regular user.

As Moderator:
1 - you can browse through applications for Service Provider, you can accept or decline. If you decline, 
    you will have to give an explanation.
2 - you can search for post by name or by category. After you open a post, you can delete it, giving explanation.
3 - you can browse through un-moderated posts, you can mark as moderated or delete. 
    If you delete them, you will have to give an explanation.
4 - you can browse through comments left by users, you can delete them, giving explanation, or mark them as moderated.
5- you can manage your personal data and information.

As admin, you can do all the same things as moderator, but you can also manage users information and roles.
You can also create new categories, or update or delete them.