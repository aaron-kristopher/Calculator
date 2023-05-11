import turtle, random
from math import sqrt, atan, degrees
wn = turtle.Screen()
wn.bgcolor('black')
position = [(200.00, 0.00), (199.81, 8.72), (199.24, 17.43), (198.29, 26.11), (196.96, 34.73), (195.26, 43.29), (193.19, 51.76),
            (190.74,60.14), (187.94,68.40), (184.78,76.54), (181.26,84.52), (177.40,92.35), (173.21,100.00), (168.68,107.46),
            (163.83,114.72), (158.67,121.75), (153.21,128.56), (147.46,135.12), (141.42,141.42), (135.12,147.46), (128.56,153.21),
            (121.75,158.67), (114.72,163.83), (107.46,168.68), (100.00,173.21), (92.35,177.40), (84.52,181.26), (76.54,184.78),
            (68.40,187.94), (60.14,190.74), (51.76,193.19), (43.29,195.26), (34.73,196.96), (26.11,198.29), (17.43,199.24), (8.72,199.81),
            (0.00,200.00), (-8.72,199.81), (-17.43,199.24), (-26.11,198.29), (-34.73,196.96), (-43.29,195.26), (-51.76,193.19), (-60.14,190.74),
            (-68.40,187.94), (-76.54,184.78), (-84.52,181.26), (-92.35,177.40), (-100.00,173.21), (-107.46,168.68), (-114.72,163.83),
            (-121.75,158.67), (-128.56,153.21), (-135.12,147.46), (-141.42,141.42), (-147.46,135.12), (-153.21,128.56), (-158.67,121.75),
            (-163.83,114.72), (-168.68,107.46), (-173.21,100.00), (-177.40,92.35), (-181.26,84.52), (-184.78,76.54), (-187.94,68.40),
            (-190.74,60.14), (-193.19,51.76), (-195.26,43.29), (-196.96,34.73), (-198.29,26.11), (-199.24,17.43), (-199.81,8.72), (-200.00,0.00),
            (-199.81,-8.72), (-199.24,-17.43), (-198.29,-26.11), (-196.96,-34.73), (-195.26,-43.29), (-193.19,-51.76), (-190.74,-60.14), (-187.94,-68.40),
            (-184.78,-76.54), (-181.26,-84.52), (-177.40,-92.35), (-173.21,-100.00), (-168.68,-107.46), (-163.83,-114.72), (-158.67,-121.75), (-153.21,-128.56),
            (-147.46,-135.12), (-141.42,-141.42), (-135.12,-147.46), (-128.56,-153.21), (-121.75,-158.67), (-114.72,-163.83), (-107.46,-168.68), (-100.00,-173.21),
            (-92.35,-177.40), (-84.52,-181.26), (-76.54,-184.78), (-68.40,-187.94), (-60.14,-190.74), (-51.76,-193.19), (-43.29,-195.26), (-34.73,-196.96),
            (-26.11,-198.29), (-17.43,-199.24), (-8.72,-199.81), (-0.00,-200.00), (8.72,-199.81), (17.43,-199.24), (26.11,-198.29), (34.73,-196.96), (43.29,-195.26),
            (51.76,-193.19), (60.14,-190.74), (68.40,-187.94), (76.54,-184.78), (84.52,-181.26), (92.35,-177.40), (100.00,-173.21), (107.46,-168.68), (114.72,-163.83),
            (121.75,-158.67), (128.56,-153.21), (135.12,-147.46), (141.42,-141.42), (147.46,-135.12), (153.21,-128.56), (158.67,-121.75), (163.83,-114.72),
            (168.68,-107.46), (173.21,-100.00), (177.40,-92.35), (181.26,-84.52), (184.78,-76.54), (187.94,-68.40), (190.74,-60.14), (193.19,-51.76),
            (195.26,-43.29), (196.96,-34.73), (198.29,-26.11), (199.24,-17.43), (199.81,-8.72)]


class Circle:
    def __init__(self, positions=[], radius=200):
        self.turtle = turtle.Turtle()
        self.heading = 0
        self.forward = radius # will represent the radius of the circle
        self.positions = positions # will take in the points on the circles circumference if there is
        self.turtle.pensize(2)
        self.turtle.hideturtle()
        self.turtle.speed(0)
        self.turtle.color('sky blue')

    # creates a new circle if you want a different one
    def draw_new_pos(self):
        for i in range(144):
            self.turtle.setheading(self.heading)
            self.turtle.forward(self.forward)
            pos = turtle.pos()
            self.positions.append(pos)
            self.turtle.goto(0,0)
            self. heading += 2.5
        return self.positions

    # draws the circle
    def draw_circle(self):
        self.turtle.shape('circle')
        self.turtle.stamp()
        self.turtle.penup()
        for i in self.positions:
            self.turtle.goto(i)
            self.turtle.pendown()
        self.turtle.goto(self.positions[0])


class Ellipse:
    def __init__(self, point, positions):
        self.turtle = turtle.Turtle()
        self.point = point  # represents the eccentric point
        self.counter = 0
        self.positions = positions
        self.turtle.hideturtle()
        self.turtle.speed(0)
        self.turtle.pensize(1)
        self.turtle.shape('circle')

    # calculates the angle between the horizontal line and the drawn line
    def calc_angle(self, x1, x2, y1, y2):
        slope = (y2 - y1) / (x2 - x1)
        if 64 >= self.counter >= 10:
            angle = degrees(atan(slope)) + 180
        else:
            angle = degrees(atan(slope))
        print((slope, angle), [x1, y1], self.counter)
        return angle

    # calculates the distance between the eccentric point to a point on the circumference of the circle
    def calc_distance(self, x1, x2, y1, y2):
        return sqrt(((x2 - x1) ** 2) + ((y2 - y1) ** 2))

    # draws the lines of the ellipse
    def draw_lines(self):
        self.turtle.penup()
        for i in self.positions[::2]:
            self.turtle.color('yellow')
            self.counter += 1

            # parses each point in the circle
            x1 = i[0]
            y1 = i[1]
            x2 = self.point[0]
            y2 = self.point[1]
            distance = self.calc_distance(x1,x2,y1,y2)
            self.turtle.goto(self.point)
            self.turtle.stamp()
            self.turtle.pendown()
            self.turtle.color('darkgrey')
            self.turtle.setheading(self.turtle.towards(x1, y1))  # sets the turtles angle towards the circle's point
            self.turtle.goto(i)

            # draws the line that represents the ellipse
            self.turtle.back(distance/2)
            self.turtle.right(90)
            self.turtle.color('white')
            self.turtle.forward(distance/2)
            self.turtle.back(distance)
            self.turtle.penup()


def main():
    circle = Circle(position)
    circle.draw_circle()
    ellipse = Ellipse(random_points(), position)
    ellipse.draw_lines()


# gives a random eccentric point
def random_points():
    r = 200
    x, y = random.randrange(-200, 201), random.randrange(-200, 201)
    dx = abs(x)
    dy = abs(y)
    pos = (x,y)

    # checks if point is within the circle
    if dx + dy <= r:
        return pos
    if dx > r or dy > r:
        return random_points()
    if (dx**2) + (dy**2) <= (r**2):
        return pos
    else:
        return random_points()


if __name__ == '__main__':

    main()
    wn.mainloop()

# print("Hello world")

