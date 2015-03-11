"""Created on Feb 28, 2014 by @author lucag.
"""

from morse.builder import ATRV, Pose, Waypoint, Environment, SemanticCamera, PassiveObject,Destination
from morse.builder import Zone
from math import pi
from morse.builder import *
import morse.helpers.colors

def build():
    # Add a robot with a position sensor and a motion controller
    
    atrv = ATRV('robot1_instance')
    atrv.properties(color='red', size=1)
    
    robot_pose = Pose()
    robot_pose.add_interface('socket')
    atrv.append(robot_pose)
    
    motion = Waypoint()
    #motion = Destination()
    #motion.properties(ControlType = 'Position')
    motion.properties(ObstacleAvoidance = False)
    motion.add_interface('socket')
    atrv.append(motion)

    proximity = Proximity()
    atrv.append(proximity)
    proximity.properties(Range = 3.0, Track = "Catch_me")
    proximity.add_stream('socket')
    proximity.add_service('socket')
    #proximity.set_tracked_tag("Catch_me")
    



    # motion_collide = Destination()
    # #motion.properties(ControlType = 'Position')
    # motion_collide.add_interface('socket')
    # atrv.append(motion_collide)
    
    # creates a new instance of the sensor
    camera = SemanticCamera('camera')

    
    
    # place your component at the correct location
    camera.translate(0.0, 0.0, 20.0)
    camera.rotate(0.0, -pi/2.0, 0.0)

    atrv.append(camera)
    
    # define one or several communication interface, like 'socket'
    camera.add_stream('socket')

    # environments/indoors-1/
    box1 = PassiveObject('environments/indoors-1/boxes', 'RedBox_big')
    box1.setgraspable()
    box1.translate(x=6.0, y=6.0, z=1)
    #box1.rotate(z=0.2)
    box1.properties(Object=True, Label = "box1_instance", Type="box", Catch_me=True, color='red', size=2)
    # box1.add_interface('socket')

    #adding a 2nd box
    box2 = PassiveObject('environments/indoors-1/boxes', 'BlueBox')
    box2.setgraspable()
    box2.translate(x=-5.0, y=4.0, z=1)
    #box2.rotate(z=0.2)
    box2.properties(Object=True, Label = "box2_instance", Type="box", Catch_me=True, color='blue', size=2)

    # #adding a 3rd box
    box3 = PassiveObject('environments/indoors-1/boxes', 'GreenBox')
    box3.setgraspable()
    box3.translate(x=-2, y=-8, z=1)
    #box3.rotate(z=0.2)
    box3.properties(Object=True, Label = "box3_instance", Catch_me=True, Type="box", color='green', size=2)

    box4 = PassiveObject('environments/indoors-1/boxes', 'RedBox_small')
    box4.setgraspable()
    box4.translate(x=3, y=-7.0, z=0)
    #box4.rotate(z=0.2)
    box4.properties(Object=True, Label = "box4_instance", Type="box", Catch_me=True, color='red', size=1)

    box5 = PassiveObject('environments/indoors-1/indoor-1', 'PinkBox')
    box5.setgraspable()
    box5.translate(x=6, y=0.0, z=0)
    #box4.rotate(z=0.2)
    box5.properties(Object=True, Label = "box5", Type="box", Catch_me=True, color='pink', size=.5)



    # Adding a table?

    desk1 = PassiveObject('environments/indoors-1/indoor-1', 'Desk_1')
    desk1.translate(x=3, y=3.0, z=0)
    #box4.rotate(z=0.2)
    desk1.properties(Object=True, Label = "desk1", Type='desk', Catch_me=True, color='white', size=1)


    # Environment
    env = Environment('indoors-1/empty-room')
    env.add_service('socket')
    
if __name__ == '__main__':
    build()
