#include <vector>

using namespace std;

class Solution {
public:
    int basic_color_;
    vector<vector<int>> image_;
    int color_;
    vector<vector<int>> floodFill(vector<vector<int>>& image, int sr, int sc, int color) {
        basic_color_ = image[sr][sc];
        image_ = vector<vector<int>>(image);
        color_ = color;
        if (basic_color_ == color_) {
            return image;
        }
        floodFill(sr, sc);
        return image_;
    }

    void floodFill(int sr, int sc) {
        if (sr >= 0 && sr < image_.size() && sc >= 0 && sc < image_[0].size()) {
            if (image_[sr][sc] == basic_color_) {
                image_[sr][sc] = color_;
                floodFill(sr - 1, sc);
                floodFill(sr + 1, sc);
                floodFill(sr, sc - 1);
                floodFill(sr, sc + 1);
            }
        }
    }
};