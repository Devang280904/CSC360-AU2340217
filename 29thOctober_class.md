# Class Notes — 29 October 2026

## Core Topics: Image Contrast, Spatial Filtering, Convolution Kernels, and Noise Reduction

---

## 1. What is Contrast in Image Processing?

**Contrast** is the difference in luminance, color, or pixel intensity that makes an object or region distinguishable from other objects and its background. 

In digital image processing, an image is represented as a 2D matrix of discrete intensity values. Contrast measures the spread and variation of these intensities across the image's dynamic range.

```text
Low Contrast Image:
Intensity Spectrum: [0] -------- [90 ======= 140] -------- [255]
(Pixels bunched in a narrow band -> murky, washed out, indistinct)

High Contrast Image:
Intensity Spectrum: [0] <================================> [255]
(Pixels span the full dynamic range -> crisp edges, deep blacks, vivid whites)
```

### Key Motivations for Analyzing and Modifying Contrast:
* **Human Visual Perception:** The human visual system is far more sensitive to relative luminance differences (contrast) than to absolute light intensity.
* **Feature Extraction & Object Detection:** High contrast between an object of interest and the background enables algorithms (such as thresholding and contour detection) to isolate boundaries accurately.
* **Overcoming Acquisition Flaws:** Poor lighting, lens flare, optical haze, or improper camera exposure settings often yield narrow, low-contrast histograms that require digital enhancement.

---

### Mathematical Formulations of Contrast

Depending on the image structure and lighting model, contrast is quantified using standard mathematical metrics:

#### 1. Weber Contrast (C_W)
Typically applied when a small visual feature or object of uniform intensity is viewed against a large, uniform background:

```text
C_W = (I - I_bg) / I_bg
```

* `I`: Luminance / intensity of the target feature
* `I_bg`: Luminance / intensity of the surrounding background

#### 2. Michelson Contrast (C_M)
Widely used for periodic stimuli, sinusoidal grating patterns, and textures where dark and bright regions are in equal proportion:

```text
C_M = (I_max - I_min) / (I_max + I_min)
```

* `I_max`: Maximum pixel intensity in the region
* `I_min`: Minimum pixel intensity in the region
* `Range`: `0 ≤ C_M ≤ 1` (where 0 means uniform gray with zero contrast, and 1 represents absolute black-to-white contrast)

#### 3. Root Mean Square (RMS) Contrast (C_RMS)
Used for complex natural images where backgrounds are non-uniform. RMS contrast is defined as the standard deviation of pixel intensities across the whole image:

```text
C_RMS = sqrt( (1 / (M * N)) * sum_{x=0}^{M-1} sum_{y=0}^{N-1} (I(x, y) - I_avg)^2 )
```

* `M × N`: Image dimensions (width × height)
* `I(x, y)`: Intensity at pixel coordinate `(x, y)`
* `I_avg`: Mean intensity across all pixels in the image

---

### Techniques for Enhancing Contrast

#### A. Linear Contrast Stretching (Min-Max Normalization)
Expands a narrow range of input intensities `[r_min, r_max]` to fill the full dynamic range `[0, L - 1]` (e.g., 0 to 255 for standard 8-bit grayscale):

```text
s = ((r - r_min) / (r_max - r_min)) * (L - 1)
```

* `r`: Original input pixel intensity
* `s`: Transformed output pixel intensity
* `r_min`, `r_max`: Minimum and maximum intensity values present in the original image
* `L`: Total intensity levels (for 8-bit images, `L = 256`, so `L - 1 = 255`)
* Values below `r_min` are clamped to `0`; values above `r_max` are clamped to `255`.

#### B. Non-Linear Power-Law (Gamma) Transformation
Adjusts image brightness and contrast according to an exponential curve:

```text
s = c * (r ^ γ)
```

* `r`: Normalized input pixel intensity (scaled between 0.0 and 1.0)
* `s`: Normalized output pixel intensity
* `c`: Scaling constant (typically 1.0)
* `γ < 1 (Gamma < 1)`: Expands dark regions, brightening shadows and revealing dark details.
* `γ > 1 (Gamma > 1)`: Compresses dark values, darkening shadows and increasing contrast in bright areas.

#### C. Histogram Equalization
Spreads out the most frequent intensity values across the entire histogram:
1. **Compute normalized histogram (Probability Density Function):**
   ```text
   p(r_k) = n_k / N_total
   ```
   where `n_k` is the count of pixels with intensity `r_k`, and `N_total` is the total number of pixels.

2. **Compute Cumulative Distribution Function (CDF):**
   ```text
   s_k = (L - 1) * sum_{j=0}^{k} p(r_j)
   ```

3. **Map each original pixel intensity `r_k` to the new equalized value `s_k`.**

---

## 2. What is an Image Filter?

An **image filter** is a mathematical operation applied to an image matrix to compute new pixel values based on an input pixel and its neighboring pixels (or frequency components).

Filtering transforms an input image `f(x, y)` into a modified output image `g(x, y)`:

```text
g(x, y) = T{ f(x, y) }
```

### Spatial Domain vs. Frequency Domain Filtering
* **Spatial Domain Filtering:** Operates directly on the image grid by sliding a small window (kernel matrix) over the pixels. The value of `g(x, y)` is computed directly from pixels in the local neighborhood of `(x, y)`.
* **Frequency Domain Filtering:** Transforms the image into frequency components using the 2D Discrete Fourier Transform (DFT):
  1. **Forward Fourier Transform:** Convert spatial image `f(x, y)` to frequency spectrum `F(u, v)`.
  2. **Frequency Filtering:** Multiply spectrum by transfer function: `G(u, v) = F(u, v) * H(u, v)`.
  3. **Inverse Fourier Transform:** Convert filtered spectrum back to spatial image: `g(x, y) = Inverse_DFT( G(u, v) )`.

---

## 3. Why Do We Need Filters?

Filters are foundational across image processing pipelines, serving four primary objectives:

```text
                          Why We Need Filters
                                   │
         ┌─────────────────┬───────┴─────────┬──────────────────┐
         ▼                 ▼                   ▼                  ▼
   Noise Reduction     Smoothing &         Sharpening &         Feature
  (Denoising pixels)   Blurring (Low-Pass) Edge Enhancement   Extraction
```

1. **Noise Reduction (Denoising):**
   * Digital sensors introduce thermal noise, transmission noise, and compression artifacts.
   * Filters suppress random pixel fluctuations (e.g., Gaussian noise or impulsive salt-and-pepper noise).
2. **Smoothing and Blurring:**
   * Removes fine, irrelevant details and high-frequency variations prior to object segmentation.
   * Blurs camera noise and bridges small gaps in lines or curves.
3. **Sharpening and Edge Enhancement:**
   * Restores edges and fine structures blurred due to optical defocusing, motion blur, or digital downsampling.
   * Highlights high-frequency spatial gradients to increase perceived image clarity.
4. **Edge and Feature Extraction:**
   * Identifies rapid changes in intensity corresponding to object boundaries, corners, ridges, and textures.

---

## 4. The Spatial Filtering Mechanism: Convolution & Kernels

### The Filter Kernel (Mask / Window)
A **kernel** is a small 2D matrix of numbers (coefficients/weights), typically of odd dimensions (3×3, 5×5, or 7×7), with a designated center called the **anchor point**.

### The Convolution / Cross-Correlation Operation
To filter an image, the kernel is centered over every pixel `(x, y)` in the input image. Each kernel weight is multiplied by the corresponding underlying pixel, and the results are summed to produce the output pixel:

```text
Input Image 3x3 Neighborhood:         3x3 Filter Kernel:
    [ p11  p12  p13 ]                      [ k11  k12  k13 ]
    [ p21  p22  p23 ]          *           [ k21  k22  k23 ]
    [ p31  p32  p33 ]                      [ k31  k32  k33 ]

Output Pixel g(x, y) = (p11*k11 + p12*k12 + p13*k13 +
                        p21*k21 + p22*k22 + p23*k23 +
                        p31*k31 + p32*k32 + p33*k33) / NormalizationFactor
```

Mathematical 2D discrete convolution formula (for kernel size `m × n`, where `a = (m - 1) / 2` and `b = (n - 1) / 2`):

```text
g(x, y) = sum_{s = -a}^{a} sum_{t = -b}^{b} [ w(s, t) * f(x - s, y - t) ]
```

> [!NOTE]
> In mathematical convolution, the kernel is flipped horizontally and vertically by 180° prior to multiplication. In cross-correlation, the kernel is applied without flipping. For symmetric kernels (such as Gaussian and Box filters), convolution and correlation yield identical results.

### Boundary & Edge Handling Strategies
When the kernel is placed on border pixels, portions of the kernel fall outside the image boundaries. Common strategies to resolve this:
* **Zero Padding:** Assume all pixels outside the image boundary have value 0. Simple, but introduces artificial dark borders.
* **Replication / Clamping:** Extend the edge pixels outward indefinitely.
* **Reflection / Mirroring:** Mirror pixels across the image boundary (e.g., `f(-1) = f(1)`). Preserves continuous gradient transitions.
* **Crop (Valid Region Only):** Only compute pixels where the entire kernel fits inside the original boundary (output image is smaller by `m - 1` in width and `n - 1` in height).

---

## 5. Linear vs. Non-Linear Filters

Image filters are broadly categorized into **Linear** and **Non-Linear** filters based on the mathematical properties of the transformation.

```text
                            Image Filters
                                  │
         ┌────────────────────────┴────────────────────────┐
         ▼                                                 ▼
   Linear Filters                                 Non-Linear Filters
  (Weighted sums)                                (Rank-order, logic)
   ├── Box / Mean Filter                          ├── Median Filter
   ├── Gaussian Blur Filter                       ├── Min / Max Filters
   ├── Laplacian Filter (Sharpening)              └── Bilateral Filter
   └── Sobel / Prewitt (Edge Detection)
```

### Linear Filters
A filter is linear if it satisfies the properties of **superposition** (additivity and homogeneity):

```text
T{ a * f1 + b * f2 } = a * T{ f1 } + b * T{ f2 }
```

#### 1. Box / Mean Filter (Low-Pass Smoothing)
Replaces each pixel with the average value of its neighborhood.
* **3×3 Box Kernel:**
  ```text
        [ 1  1  1 ]
  1/9 * [ 1  1  1 ]
        [ 1  1  1 ]
  ```
* **Pros:** Fast and straightforward to compute.
* **Cons:** Blurs sharp edges indiscriminately; can introduce rectangular artifacts.

#### 2. Gaussian Filter (Weighted Smoothing)
Weights decay radially from the center according to a 2D Gaussian distribution:

```text
G(x, y) = (1 / (2 * π * σ²)) * exp( -(x² + y²) / (2 * σ²) )
```

* **3×3 Discrete Gaussian Approximation Kernel:**
  ```text
         [ 1  2  1 ]
  1/16 * [ 2  4  2 ]
         [ 1  2  1 ]
  ```
* **Pros:** Mimics biological vision; produces smooth, natural blurring without harsh box artifacts.

#### 3. Laplacian Filter (Sharpening / High-Pass)
Computes the second spatial derivative of the image to detect rapid intensity transitions:

```text
∇²f = (∂²f / ∂x²) + (∂²f / ∂y²)
```

* **Standard 3×3 Laplacian Kernels:**
  ```text
  Without Diagonals:         With Diagonals:
     [  0  -1   0 ]             [ -1  -1  -1 ]
     [ -1   4  -1 ]             [ -1   8  -1 ]
     [  0  -1   0 ]             [ -1  -1  -1 ]
  ```
* Sharpened Output: `g(x, y) = f(x, y) - ∇²f(x, y)`.

#### 4. Sobel Filter (First Derivative Edge Detection)
Computes horizontal (`G_x`) and vertical (`G_y`) spatial gradients:

```text
        [ -1  0  +1 ]                  [ -1  -2  -1 ]
  G_x = [ -2  0  +2 ]            G_y = [  0   0   0 ]
        [ -1  0  +1 ]                  [ +1  +2  +1 ]
```

* **Gradient Magnitude:** `|∇f| = sqrt(G_x² + G_y²) ≈ |G_x| + |G_y|`
* **Gradient Direction:** `θ = atan2(G_y, G_x)`

---

### Non-Linear Filters
Non-linear filters do not satisfy the superposition principle. Pixel values are determined using statistical ordering, logical decisions, or adaptive thresholds.

#### 1. Median Filter (Rank-Order Filter)
Replaces the central pixel with the **median** of all pixel values within the sliding window:
1. Extract the N×N neighborhood values: e.g., `[12, 14, 15, 255, 13, 16, 14, 12, 15]`.
2. Sort values in ascending order: `[12, 12, 13, 14, 14, 15, 15, 16, 255]`.
3. Assign the median value (`14`) to the center pixel.

> [!IMPORTANT]
> The Median filter is extraordinarily effective at eliminating **impulse noise (salt-and-pepper noise)** without blurring sharp object edges. Extreme noise spikes (0 or 255) are moved to the ends of the sorted array and discarded by the median selection.

#### 2. Min and Max Filters (Morphological Foundations)
* **Min Filter:** Assigns the minimum intensity in the neighborhood (darkens bright areas; basis for Morphological *Erosion*).
* **Max Filter:** Assigns the maximum intensity in the neighborhood (brightens dark areas; basis for Morphological *Dilation*).

#### 3. Bilateral Filter (Edge-Preserving Smoothing)
Combines spatial proximity weights (Gaussian in space) with pixel intensity similarity weights (Gaussian in color/range):
* Pixels close in spatial distance **and** similar in intensity are averaged together.
* Pixels across high-contrast edges have drastically different intensities and are ignored, preserving crisp edges while smoothing flat surfaces.

---

## 6. Implementation in Java (`java.awt.image`)

Java's standard desktop imaging framework provides high-performance classes for filtering and contrast manipulation.

### Approach 1: Using `ConvolveOp` and `Kernel`
For linear spatial convolution, Java provides `java.awt.image.ConvolveOp`:

```java
package devang.imaging;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class FilterUtils {

    /**
     * Applies a 3x3 Gaussian Blur filter to an image.
     *
     * @param source the input BufferedImage
     * @return a new filtered BufferedImage
     */
    public static BufferedImage applyGaussianBlur(BufferedImage source) {
        // 3x3 Gaussian approximation kernel (weights sum to 1.0)
        float[] gaussianMatrix = {
            1f / 16f, 2f / 16f, 1f / 16f,
            2f / 16f, 4f / 16f, 2f / 16f,
            1f / 16f, 2f / 16f, 1f / 16f
        };

        Kernel kernel = new Kernel(3, 3, gaussianMatrix);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        return op.filter(source, null);
    }

    /**
     * Applies an unsharp/edge-sharpening filter to an image.
     *
     * @param source the input BufferedImage
     * @return sharpened BufferedImage
     */
    public static BufferedImage applySharpen(BufferedImage source) {
        float[] sharpenMatrix = {
             0.0f, -1.0f,  0.0f,
            -1.0f,  5.0f, -1.0f,
             0.0f, -1.0f,  0.0f
        };

        Kernel kernel = new Kernel(3, 3, sharpenMatrix);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        return op.filter(source, null);
    }
}
```

---

### Approach 2: Direct Pixel Manipulation (Median Filter & Boundary Clamping)
Non-linear filters like the Median filter cannot be executed with linear `ConvolveOp` and require direct raster/pixel traversal:

```java
package devang.imaging;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class NonLinearFilters {

    /**
     * Applies a 3x3 Median Filter to remove salt-and-pepper impulse noise.
     *
     * @param src the source grayscale/RGB image
     * @return noise-reduced BufferedImage
     */
    public static BufferedImage applyMedianFilter(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage dest = new BufferedImage(width, height, src.getType());

        int[] windowR = new int[9];
        int[] windowG = new int[9];
        int[] windowB = new int[9];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int count = 0;

                // Collect neighborhood with border clamping
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        int nx = Math.min(Math.max(x + dx, 0), width - 1);
                        int ny = Math.min(Math.max(y + dy, 0), height - 1);

                        int rgb = src.getRGB(nx, ny);
                        windowR[count] = (rgb >> 16) & 0xFF;
                        windowG[count] = (rgb >> 8) & 0xFF;
                        windowB[count] = rgb & 0xFF;
                        count++;
                    }
                }

                // Sort color channels independently
                Arrays.sort(windowR);
                Arrays.sort(windowG);
                Arrays.sort(windowB);

                // Pick the median (index 4 in a 9-element array)
                int medianR = windowR[4];
                int medianG = windowG[4];
                int medianB = windowB[4];

                int finalRgb = (0xFF << 24) | (medianR << 16) | (medianG << 8) | medianB;
                dest.setRGB(x, y, finalRgb);
            }
        }
        return dest;
    }
}
```

---

## 7. Linear vs. Non-Linear Filtering Comparison

| Attribute | Linear Filters (e.g., Mean, Gaussian) | Non-Linear Filters (e.g., Median, Bilateral) |
| :--- | :--- | :--- |
| **Mathematical Basis** | Linear weighted sum: `∑ (w_i * p_i)` | Sorting, ranking, conditional logic |
| **Superposition** | Satisfied: `T{f + g} = T{f} + T{g}` | Not satisfied |
| **Frequency Interpretation** | Direct frequency transfer function `H(u, v)` | No direct transfer function in Fourier domain |
| **Edge Preservation** | Blurs sharp edges along with noise | Preserves crisp edges while removing noise spikes |
| **Computational Complexity**| `O(K²)` (separable down to `O(2K)`) | `O(K² log K)` due to window sorting |
| **Ideal Noise Type** | Uniform noise, Gaussian zero-mean noise | Impulsive salt-and-pepper noise, dead pixels |

---

## Summary Cheat Sheet

| Topic | Definition | Core Mechanism / Equation | Primary Use Case |
| :--- | :--- | :--- | :--- |
| **Contrast** | Relative intensity variation between object and background | `C_M = (I_max - I_min) / (I_max + I_min)` | Feature distinction, segmentation, visual clarity |
| **Contrast Stretching** | Linear remapping of intensity values | `s = ((r - r_min) / (r_max - r_min)) * (L - 1)` | Expanding narrow histograms to use full dynamic range |
| **Spatial Filter** | Local neighborhood pixel transformation | `g(x, y) = ∑∑ [w(s, t) * f(x-s, y-t)]` | Noise removal, edge enhancement, feature extraction |
| **Kernel / Mask** | Small matrix of weights with anchor point | `1/16 * [1,2,1; 2,4,2; 1,2,1]` (3×3 matrix) | Sliding window multiplier across the image grid |
| **Mean / Box Filter** | Linear low-pass average filter | `Average: (1/N²) * ∑ p_i` | Rapid image smoothing; suppresses uniform noise |
| **Gaussian Filter** | Distance-weighted linear smoothing filter | `G(x, y) ∝ exp(-(x² + y²) / 2σ²)` | Natural blurring without square artifacts |
| **Median Filter** | Non-linear rank-order filter | `median(pixel_neighborhood)` | Eliminating salt-and-pepper noise while preserving sharp edges |
| **Sobel / Laplacian** | Gradient and second-derivative filters | `Sobel: √(Gx² + Gy²)`; `Laplacian: ∇²f` | Sharpening, line detection, boundary/edge extraction |
