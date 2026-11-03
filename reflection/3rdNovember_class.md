# Class Notes — 3 November 2026

## Core Topics: RGB vs. RGBA Color Models, 32-Bit Integer Pixel Packing, Memory Channel Layouts, and Endianness (Little-Endian vs. Big-Endian)

---

## 1. How is RGB Arranged in an Image?

The **RGB color model** is an additive color representation based on the human eye's photoreceptor response to three primary wavelengths of light: **Red**, **Green**, and **Blue**. 

In digital imagery, an RGB image is organized as a 2D matrix of pixels, where each individual pixel is defined by three distinct numerical values corresponding to the intensity of these primary channels:

```text
Pixel (x, y) = [ Red Intensity, Green Intensity, Blue Intensity ]
```

```text
               (0, 255, 0) Green
                    ▲
                    │
                    │
                    │
 (0, 0, 0) Black ───┼───────────► (255, 0, 0) Red
                   / 
                  /  
                 ▼ (0, 0, 255) Blue
 
 All channels at 255: (255, 255, 255) = Pure White
 All channels at 0:   (0, 0, 0)       = Pure Black
```

### Channel Depth & Intensity Range:
* **8 Bits per Channel (Standard 24-bit TrueColor):**
  * Each channel is allocated 1 byte (8 bits), providing 2⁸ = 256 discrete intensity levels ranging from **0** (no light / zero intensity) to **255** (full saturation / maximum intensity).
  * Total potential color gamut: `256 × 256 × 256 = 16,777,216` unique colors (approximately 16.7 million).
* **Common Color Combinations:**
  * Red: `(255, 0, 0)`
  * Green: `(0, 255, 0)`
  * Blue: `(0, 0, 255)`
  * Yellow: `(255, 255, 0)` (Red + Green)
  * Cyan: `(0, 255, 255)` (Green + Blue)
  * Magenta: `(255, 0, 255)` (Red + Blue)
  * Mid-Gray: `(128, 128, 128)`

---

## 2. How is RGBA Arranged in an Image? (The Alpha Channel)

**RGBA** extends the standard RGB color model by adding a fourth component: the **Alpha channel (α)**. 

The Alpha channel does not represent a visible color wavelength; instead, it defines the **opacity** or **transparency** of the pixel.

```text
Pixel (x, y) = [ Red, Green, Blue, Alpha ]
```

### Alpha Channel Values:
* Like the color channels, Alpha is typically represented as an 8-bit unsigned integer (`0` to `255`):
  * **0 (`0x00`):** Completely transparent (invisible; underlying background is 100% visible).
  * **255 (`0xFF`):** Completely opaque (fully solid; underlying background is 100% occluded).
  * **1 to 254:** Semi-transparent / translucent (allows background pixels to bleed through based on the opacity percentage).

### Why RGBA is Essential:
1. **Layering & Compositing:** Enables multi-layered rendering in UI frameworks (JavaFX, Swing, CSS), 2D game sprites, and photo editors.
2. **Anti-Aliased Edges:** Smooths high-contrast boundaries of text and geometric shapes against arbitrary backgrounds by blending edge pixels.
3. **Irregular Shapes & Cutouts:** Formats like PNG and WebP use alpha masks to display non-rectangular graphic assets without showing an artificial white or black bounding box.

### Alpha Blending / Compositing (The "Over" Operator)
When rendering a foreground pixel `A` over a background pixel `B`, the resulting displayed color `C_out` is computed using linear interpolation:

```text
α = Alpha_A / 255.0   (normalized opacity between 0.0 and 1.0)

C_out = (α * Color_A) + ((1.0 - α) * Color_B)
```

> [!NOTE]
> **Straight Alpha vs. Premultiplied Alpha:**
> * **Straight Alpha:** The RGB channels store the raw color values unchanged, and alpha is applied during rendering.
> * **Premultiplied Alpha:** The RGB values are multiplied by α ahead of time: `R' = R * α`, `G' = G * α`, `B' = B * α`. Modern GPUs and game engines prefer premultiplied alpha because it simplifies compositing arithmetic and avoids dark fringing artifacts around translucent borders.

---

## 3. Pixel Storage as a 32-Bit Integer (`int32`)

In high-performance image processing, each pixel is frequently stored not as four separate byte variables, but as a single **32-bit signed or unsigned integer** (`int32` / primitive `int` in Java).

```text
32 bits = 4 bytes × 8 bits per channel (Alpha, Red, Green, Blue)
```

```text
Bit Position:
31            24 23            16 15             8 7              0
+---------------+---------------+---------------+---------------+
|  Alpha (A)    |   Red (R)     |   Green (G)   |   Blue (B)    |
|    8 bits     |    8 bits     |    8 bits     |    8 bits     |
+---------------+---------------+---------------+---------------+
 Byte 3 (MSB)        Byte 2          Byte 1        Byte 0 (LSB)
```

### Why Use 32-Bit Integers Instead of 24-Bit Structures?
1. **Word Alignment on Modern CPU Architectures:**
   * Modern 32-bit and 64-bit CPUs read and write memory most efficiently when data addresses are aligned to 4-byte or 8-byte boundaries.
   * Storing 24 bits (3 bytes) forces misaligned memory access, requiring extra CPU cycles and complex shifting across cache-line boundaries.
   * Allocating a clean 4 bytes per pixel guarantees optimal bus throughput and cache utilization.
2. **SIMD & Vectorization:**
   * Single-Instruction Multiple-Data (SIMD) processor instructions (AVX, NEON, SSE) can process four 8-bit channels in parallel within single vector registers.
3. **Atomic Pixel Manipulation:**
   * An entire pixel can be copied, swapped, or loaded in a single CPU instruction (e.g., `mov eax, [rsi]`).

### Bitwise Packing and Unpacking Operations

#### Bitwise Packing (Combining 4 Channels into 1 Integer):
Each byte is positioned into its designated 8-bit slot using bitwise left-shift (`<<`) and combined using bitwise OR (`|`):

```text
packedPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
```

#### Bitwise Unpacking (Extracting Channels from 1 Integer):
Each channel is isolated by shifting the target byte down to the lowest 8 bits using bitwise right-shift (`>>` or `>>>`) and masking out higher bits using bitwise AND with `0xFF` (`255`):

```text
alpha = (packedPixel >> 24) & 0xFF;
red   = (packedPixel >> 16) & 0xFF;
green = (packedPixel >> 8)  & 0xFF;
blue  = packedPixel & 0xFF;
```

---

## 4. How Can These 4 Bytes Be Ordered in Memory?

In memory buffers, pixel channels can be organized in either **Planar** or **Packed (Interleaved)** layouts:
* **Planar Format:** All Red bytes for the entire image are stored consecutively, followed by all Green bytes, Blue bytes, and Alpha bytes (`RRR...GGG...BBB...AAA...`). (Common in video codecs like YUV420p).
* **Packed / Interleaved Format:** All color components of a single pixel are stored contiguously before moving to the next pixel (`RGBARGBARGBA...`). (Standard in graphics memory, framebuffers, and bitmap files).

In packed memory, the exact sequence of the four bytes `[R, G, B, A]` varies depending on the platform, graphics API, and file container.

```text
Memory Offset:     +0      +1      +2      +3
RGBA Layout:     [ Red ] [ Green ] [ Blue ] [ Alpha ]
ARGB Layout:     [ Alpha ] [ Red ] [ Green ] [ Blue ]
BGRA Layout:     [ Blue ] [ Green ] [ Red ] [ Alpha ]
ABGR Layout:     [ Alpha ] [ Blue ] [ Green ] [ Red ]
```

### The 4 Common Channel Orderings:

1. **RGBA (Red, Green, Blue, Alpha):**
   * **Sequence:** Byte 0 = Red, Byte 1 = Green, Byte 2 = Blue, Byte 3 = Alpha.
   * **Used By:** OpenGL, WebGL, HTML5 `<canvas>`, CSS `rgba()`, PNG file byte streams, Android NDK surfaces.
   * **Characteristics:** Most intuitive order for programmers reading left-to-right.

2. **ARGB (Alpha, Red, Green, Blue):**
   * **Sequence:** Byte 0 = Alpha, Byte 1 = Red, Byte 2 = Green, Byte 3 = Blue.
   * **Used By:** Java AWT/Swing (`BufferedImage.TYPE_INT_ARGB`), Windows GDI+, Android `Bitmap.Config.ARGB_8888`, QuickTime.
   * **Characteristics:** Puts the alpha mask in the most significant position when read as a big-endian 32-bit scalar.

3. **BGRA (Blue, Green, Red, Alpha):**
   * **Sequence:** Byte 0 = Blue, Byte 1 = Green, Byte 2 = Red, Byte 3 = Alpha.
   * **Used By:** Microsoft DirectX / Direct3D, Windows DIBs (Device-Independent Bitmaps), Vulkan framebuffers, Apple Metal, Cairo graphics.
   * **Characteristics:** Native representation for standard Windows graphics cards and display drivers.

4. **ABGR (Alpha, Blue, Green, Red):**
   * **Sequence:** Byte 0 = Alpha, Byte 1 = Blue, Byte 2 = Green, Byte 3 = Red.
   * **Used By:** Specialized hardware framebuffers, SGI workstations, some TIFF file implementations, reverse byte streams.

> [!WARNING]
> **Channel Order Mismatches Produce Visual Glitches:**
> If software expects RGBA but receives BGRA data, Red and Blue channels are swapped: human faces appear blue (the classic "Smurf effect"), and skies appear orange. If Alpha is swapped with a color channel, the image may appear completely transparent or wildly discolored.

---

## 5. Endianness (Big-Endian vs. Little-Endian)

**Endianness** refers to the convention used by computer hardware to order the individual bytes of a multi-byte word in physical memory.

```text
Consider the 32-bit hexadecimal value: 0x12345678
Highest value byte (MSB - Most Significant Byte): 0x12
Lowest value byte  (LSB - Least Significant Byte): 0x78
```

```text
Memory Address:       0x00       0x01       0x02       0x03
                  +----------+----------+----------+----------+
Big-Endian:       |   0x12   |   0x34   |   0x56   |   0x78   |
                  +----------+----------+----------+----------+
                   MSB (12)                          LSB (78)
                   Stored at Lowest Address

                  +----------+----------+----------+----------+
Little-Endian:    |   0x78   |   0x56   |   0x34   |   0x12   |
                  +----------+----------+----------+----------+
                   LSB (78)                          MSB (12)
                   Stored at Lowest Address
```

### Definitions:
* **Big-Endian ("Network Byte Order"):**
  * The **Most Significant Byte (MSB)** is stored at the lowest memory address (first in sequence).
  * Mimics the natural human reading order of numbers (left to right).
  * Used in Internet network protocols (TCP/IP header formats), older Motorola 68k, SPARC, and conceptually in the Java Virtual Machine bytecode.
* **Little-Endian:**
  * The **Least Significant Byte (LSB)** is stored at the lowest memory address.
  * Used by virtually all modern desktop and server processors: **x86, x86-64 (Intel, AMD)**, and standard **ARM** chips (Apple Silicon, Android).
  * Mathematically convenient for CPU arithmetic, as carry-propagation and pointer-casting between 8-bit, 16-bit, and 32-bit types occur at the same memory address.

---

### The Crucial Intersection: 32-Bit Pixel Integers vs. Memory Byte Streams

A frequent source of developer confusion is the difference between an **integer in a CPU register** and the **bytes stored in RAM**.

Suppose a program defines a 32-bit ARGB pixel in code:
```text
Hex Value: 0xAARRGGBB
Where:
  Alpha = 0xAA (Most Significant Byte)
  Red   = 0x11
  Green = 0x22
  Blue  = 0x33 (Least Significant Byte)
```

Depending on the CPU architecture, this single integer is laid out in physical RAM bytes as follows:

```text
Register Value: [ 0xAA, 0x11, 0x22, 0x33 ]  (ARGB)

Physical RAM on Big-Endian CPU:
Address:    [+0]     [+1]     [+2]     [+3]
Byte:       0xAA     0x11     0x22     0x33
Channel:   Alpha     Red     Green     Blue   -> Byte stream is ARGB

Physical RAM on Little-Endian CPU (Intel/AMD/ARM):
Address:    [+0]     [+1]     [+2]     [+3]
Byte:       0x33     0x22     0x11     0xAA
Channel:    Blue    Green     Red     Alpha   -> Byte stream is BGRA!
```

> [!IMPORTANT]
> A 32-bit integer formatted as `ARGB` in CPU registers (`0xAARRGGBB`) will be written into physical memory on an x86 Little-Endian machine as the byte sequence `[B, G, R, A]`.
> 
> This is why DirectX and Windows graphics drivers historically standardized on **BGRA** byte order—it represents native memory dumps of 32-bit ARGB CPU registers on Little-Endian hardware without requiring byte-swapping instructions!

---

## 6. Implementation in Java (`java.awt.image` & `java.nio`)

The following production-ready utility class demonstrates bitwise pixel packing, unpacking, and endian-aware byte layout handling in Java.

```java
package devang.imaging;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PixelMemoryUtils {

    /**
     * Packs individual 8-bit RGBA components into a single 32-bit ARGB integer.
     *
     * @param a alpha channel (0 - 255)
     * @param r red channel   (0 - 255)
     * @param g green channel (0 - 255)
     * @param b blue channel  (0 - 255)
     * @return 32-bit packed integer (0xAARRGGBB)
     */
    public static int packARGB(int a, int r, int g, int b) {
        return ((a & 0xFF) << 24) |
               ((r & 0xFF) << 16) |
               ((g & 0xFF) << 8)  |
               (b & 0xFF);
    }

    /**
     * Extracts color channels from an ARGB integer into an array: [Alpha, Red, Green, Blue].
     *
     * @param argbPixel packed 32-bit integer
     * @return int array containing individual channel intensities
     */
    public static int[] unpackARGB(int argbPixel) {
        int a = (argbPixel >>> 24) & 0xFF;
        int r = (argbPixel >>> 16) & 0xFF;
        int g = (argbPixel >>> 8)  & 0xFF;
        int b = argbPixel & 0xFF;
        return new int[]{ a, r, g, b };
    }

    /**
     * Demonstrates how endianness alters the byte sequence of a 32-bit pixel integer.
     */
    public static void demonstrateEndianBytes(int argbPixel) {
        // Allocate 4 bytes for testing
        ByteBuffer buffer = ByteBuffer.allocate(4);

        // Store as Big-Endian
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(argbPixel);
        byte[] bigEndianBytes = buffer.array().clone();

        // Store as Little-Endian
        buffer.clear();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(argbPixel);
        byte[] littleEndianBytes = buffer.array().clone();

        System.out.printf("Original Integer: 0x%08X%n", argbPixel);
        System.out.print("Big-Endian RAM Bytes:    ");
        printHexBytes(bigEndianBytes);

        System.out.print("Little-Endian RAM Bytes: ");
        printHexBytes(littleEndianBytes);
    }

    private static void printHexBytes(byte[] bytes) {
        for (byte b : bytes) {
            System.out.printf("[0x%02X] ", b);
        }
        System.out.println();
    }
}
```

---

## 7. Channel Ordering & Platform Compatibility Matrix

| Format Name | Byte Sequence in RAM (`+0, +1, +2, +3`) | 32-Bit Register Equivalent (Little-Endian) | Primary Frameworks / Environments |
| :--- | :--- | :--- | :--- |
| **RGBA** | `[Red] [Green] [Blue] [Alpha]` | `0xAABBGGRR` | OpenGL, WebGL, PNG Files, HTML5 Canvas, Android NDK |
| **ARGB** | `[Alpha] [Red] [Green] [Blue]` | `0xBBGGRRAA` | Java AWT/Swing (`TYPE_INT_ARGB`), QuickTime, Android Bitmap |
| **BGRA** | `[Blue] [Green] [Red] [Alpha]` | `0xAARRGGBB` | Windows DirectX, Vulkan, Apple Metal, Cairo, GDI |
| **ABGR** | `[Alpha] [Blue] [Green] [Red]` | `0xRRGGBBAA` | SGI Workstations, Specialized Framebuffers, Reverse Streams |

---

## Summary Cheat Sheet

| Concept | Definition & Memory Footprint | Core Mechanism / Operation | Key Practical Considerations |
| :--- | :--- | :--- | :--- |
| **RGB Model** | 3-channel additive color; 24 bits (3 bytes) per pixel | Primary components: Red, Green, Blue (0 to 255) | 16.7M colors; lacks transparency; may cause unaligned memory access |
| **RGBA Model** | 4-channel representation; 32 bits (4 bytes) per pixel | Adds Alpha channel (α) for opacity | Supports alpha blending, layer compositing, and transparent sprites |
| **`int32` Packing** | Storing 4 bytes inside a single 32-bit machine word | `(A << 24) \| (R << 16) \| (G << 8) \| B` | Aligns data on 4-byte boundaries; unlocks fast CPU instructions & SIMD |
| **Channel Ordering**| Spatial sequence of color bytes in memory buffers | Variants: RGBA, ARGB, BGRA, ABGR | Mismatched formats cause blue-shifted images ("Smurf effect") or broken alpha |
| **Big-Endian** | MSB stored at lowest memory address | Network byte order: `[Byte 3] [Byte 2] [Byte 1] [Byte 0]` | Standard in network protocols, file formats (JPEG), and JVM abstract order |
| **Little-Endian** | LSB stored at lowest memory address | Intel/ARM hardware order: `[Byte 0] [Byte 1] [Byte 2] [Byte 3]` | ARGB integers in CPU registers are written to RAM as BGRA byte sequences |
