# Class Reflection — 10 September 2026


### Streaming and downloading

I thought, streaming and downloading are almost the same because in both cases data is coming from somewhere else. But the main difference is how we use that data.

In streaming, we can start using the data before the complete data has arrived. For example, while watching an online video, the whole video is not downloaded first. Some part is stored in a buffer and playback starts.

Examples of streaming:
- Online videos
- Internet radio
- Live camera
- Cloud gaming
- Remote desktop

In downloading, the complete file is transferred and stored locally. After that we can use it without needing the network, like downloading a movie or software installer.

Streaming can also change quality depending on network speed. If internet speed becomes low, a lower quality segment can be selected. This is called adaptive bitrate streaming.

Buffering is basically there to handle small network interruptions. If data is coming slower than the rate at which we are consuming it, eventually the buffer becomes empty and we see buffering.

### Streaming in graphics

Streaming is not only for videos. It can also be used in graphics applications.

For example, in a large open-world game, it is not necessary to load every texture and 3D model into memory at the starting point. The game can load the assets around the player and remove assets which are not needed anymore.

This helps in reducing initial loading time and memory usage.

Cloud gaming is another example. Here the actual game runs on a remote server. The server renders the frames and sends the video/audio to our device. Our inputs like keyboard, mouse or controller are sent back to the server.

So the local computer does not need to do all the heavy rendering. But the network has to be good because latency and jitter can affect the experience.

Remote desktop and scientific/CAD visualization can also use similar ideas, where heavy processing is done remotely and the result is sent to the client.

For VR and AR, latency becomes even more important because even small delays can affect the experience.

### Server-side and client-side rendering

In Server-Side Rendering (SSR), the server prepares the HTML and sends the rendered page to the browser. The browser can show useful content earlier, and JavaScript can later make the page interactive.

In Client-Side Rendering (CSR), the browser gets the basic page and JavaScript, and then the JavaScript runs in the browser and creates/updates the UI using the required data.

A simple way I remember it is:

SSR -> server does more of the initial page rendering.

CSR -> browser does more of the rendering.

SSR can be useful for faster initial content visibility and search engine indexing. But the server has to do more work and highly personalized pages can be harder to cache.

CSR is good for highly interactive applications and after the first load, navigation can feel very fast. The disadvantage is that the browser has to download and execute JavaScript before the complete UI is available.

Modern frameworks can actually use a mixture of both instead of using only SSR or only CSR.

Also, neither method automatically makes a website accessible. Things like proper HTML, keyboard support, labels, focus and good contrast are still required.

### Storing images

Another topic was how images are stored in applications.

Normally we don't need to keep a large image directly inside a normal database. A common approach is:

Image file -> object storage  
Image information -> database

For example, object storage such as Amazon S3 can store the actual image. The database can store information like:

- file name
- object key or URL
- image type
- width and height
- file size
- owner/user id
- upload time
- permissions
- checksum

Object storage is suitable for large files because it is designed for storing lots of objects and can scale easily.

For private images, signed URLs can also be used so that the image is accessible only for a limited time or by an authorized user.

It is technically possible to store image data as BLOB in a database also. This can make sense for small files or when strong transaction consistency is important. But for large media applications, object storage + database metadata is a common design.

### Why AWT is called Abstract Window Toolkit

AWT stands for Abstract Window Toolkit. The word "Abstract" is mainly because Java provides a common API and hides the platform-specific details.

For example, the Java program can use an AWT `Button`, but the actual window/component is connected with the operating system's native GUI system.

So roughly:

Java application -> AWT API -> Platform-specific toolkit / peers -> Operating system window system

Because of this, the Java programmer doesn't have to write completely different GUI code for every operating system.

Some common AWT classes are `Frame`, `Button`, `TextField` and `Graphics`.

### AWT and Swing

Swing is built on top of AWT. AWT provides the basic windowing, event handling, layouts and graphics infrastructure.

AWT components like `Button` and `Frame` are generally heavyweight because they are associated with native OS components.

Swing provides components like `JButton` and `JFrame`, and most Swing components are lightweight. Swing also supports pluggable look and feel.

So Swing did not completely replace AWT. It still uses many AWT features such as events, layouts, fonts, colors and graphics.

