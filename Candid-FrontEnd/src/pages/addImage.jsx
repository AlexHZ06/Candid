import { useState } from "react"
import PHeader from "../compnents/pheader"
import Radial from "../compnents/radial"
import { Link } from "react-router-dom"

function AddImage(){

    const [active, setActive] = useState("None selected")
    const [macCost, setMaxCost] = useState(0)
    const [lighting, setLighting] = useState([])
    const [mood, setMood] = useState([])
    const [style, setStyle] = useState([])
    const [activeTag, setActiveTag] = useState("")

    function clearTags(){

        setStyle([])
        setMood([])
        setLighting([])

    }

    function lightingClick(text) {

        setLighting(prev => {
            if (prev.includes(text)) {
                return prev.filter(tag => tag !== text);
            } else {
                return [...prev, text];
            }
        });
    }

    function moodClick(text) {

        setMood(prev => {
            if (prev.includes(text)) {
                return prev.filter(tag => tag !== text);
            } else {
                return [...prev, text];
            }
        });
    }

    function styleClick(text) {

        setStyle(prev => {
            if (prev.includes(text)) {
                return prev.filter(tag => tag !== text);
            } else {
                return [...prev, text];
            }
        });
    }    

    function categoryClick(text){

        if(text === active){

            setActive("None selected")

        }
        else{
            setActive(text)
        }

    }


    return(
        <div className="
        
            w-screen 
            min-h-screen 
            flex
            flex-col
            items-center
                  
        ">
            <PHeader active="Gallery"/>
            <div className="
            
                flex
                border
                border-neutral-300
                w-5/10
                h-[80vh]
                shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                flex-col
                items-center
                rounded-md
                mt-10
                hidden  
                
            
            ">
                <input placeholder="Title" type="text" className="
                
                    border-2
                    mt-10
                    h-10
                    rounded-md
                    w-[80vh]
                    pl-2
                    border-neutral-500
                    hover:border-black

                    transition
                    duration-100

                "/>  
                    <textarea placeholder="Desctiption" className="
                    
                        w-[80vh]
                        border-2
                        rounded-md
                         border-neutral-500
                         hover:border-black
                        transition
                        duration-200
                        mt-5
                        h-[15vh]
                        pl-2
                        resize-none

                    
                    "/> 
                    <label
                    className="
                        flex flex-col
                        items-center
                        justify-center
                        w-[80vh]
                        h-[40vh]
                        mt-5
                        border-2
                        rounded-md
                        border-neutral-500
                        hover:border-black
                        transition
                        duration-200
                        cursor-pointer
                    "
                    >
                    <div className="text-4xl">
                        <img src="src\resources\Image--Streamline-Rounded-Streamline-Material-Free.svg" className="
                        
                            
                        
                        "/>
                    </div>

                    <p className="mt-2 text-gray-600">
                        Click to upload an image
                    </p>
                    <input
                        type="file"
                        accept="image/*"
                        className="hidden"
                    />
                    </label>
                    <p className="
                    
                        text-red-600
                    
                    ">Error Message</p>   
                <div className="
                
                        flex
                        flex-row
                        gap-10
                
                ">
                    <button className="
                
                        bg-black
                        w-[20vh]
                        h-10
                        rounded-md
                        text-white

                        hover:bg-gray-900
                        mt-10

                        transition
                        duration-100

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black

                        

                    
                    ">Submit</button>
                    <button className="
                
                        bg-black
                        w-[20vh]
                        h-10
                        rounded-md
                        text-white

                        hover:bg-gray-900
                        mt-10

                        transition
                        duration-100

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black

                        

                    
                    ">Back</button> 
                </div>                         
            </div>
            <div className={`
            
                flex
                border
                border-neutral-300
                w-5/10
                
                hidden
                shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                flex-col
                items-center
                rounded-md
                mt-20
            
            `}>
                <div className="
                
                    w-[90vh]
                    mt-5

                ">
                    <p className="
                    
                        font-light
                    
                    ">Category: {active}</p>
                </div>
                <div className="
                
                    border-b-2
                    w-[90vh]
                    border-neutral-300
                    h-[8vh]
                    flex
                    gap-1
                    shrink-0
                    flex-row
                    overflow-y-auto
                    items-center

                
                ">  
                    <Radial active={active} onClick={()=>categoryClick("Wedding")} text={"Wedding"}/>
                    <Radial active={active} onClick={()=>categoryClick("Street")} text={"Street"}/>
                    <Radial active={active} onClick={()=>categoryClick("Portrait")} text={"Portrait"}/>
                    <Radial active={active} onClick={()=>categoryClick("Event")} text={"Event"}/>
                    <Radial active={active} onClick={()=>categoryClick("Fine Art")} text={"Fine Art"}/>
                    <Radial active={active} onClick={()=>categoryClick("Landscape")} text={"Landscape"}/>
                    <Radial active={active} onClick={()=>categoryClick("Wild Life")} text={"Wild Life"}/>  
                    <Radial active={active} onClick={()=>categoryClick("Fashion")} text={"Fashion"}/>  
                    <Radial active={active} onClick={()=>categoryClick("Comercial")} text={"Comercial"}/>  
                    <Radial active={active} onClick={()=>categoryClick("Architectural")} text={"Architectural"}/>  
                    <Radial active={active} onClick={()=>categoryClick("Food")} text={"Food"}/>  
                    <Radial active={active} onClick={()=>categoryClick("Product")} text={"Product"}/> 
                    <Radial active={active} onClick={()=>categoryClick("Sports")} text={"Sports"}/>
                    <Radial active={active} onClick={()=>categoryClick("Documentary")} text={"Documentary"}/>
                    <Radial active={active} onClick={()=>categoryClick("Photojournalism")} text={"Photo Journalism"}/>
                    <Radial active={active} onClick={()=>categoryClick("Macro")} text={"Macro"}/>
                    <Radial active={active} onClick={()=>categoryClick("Astrophotography")} text={"Astrophotography"}/>
                    <Radial active={active} onClick={()=>categoryClick("Aerial")} text={"Aerial"}/>
                    <Radial active={active} onClick={()=>categoryClick("Underwater")} text={"Underwater"}/>
                    <Radial active={active} onClick={()=>categoryClick("Night")} text={"Night"}/>
                    <Radial active={active} onClick={()=>categoryClick("Long-Exposure")} text={"Long-Exposure"}/>
                    <Radial active={active} onClick={()=>categoryClick("Black & White")} text={"Black & White"}/>
                    <Radial active={active} onClick={()=>categoryClick("Scientific")} text={"Scientific"}/>
                    <Radial active={active} onClick={()=>categoryClick("Medical")} text={"Medical"}/>
                    <Radial active={active} onClick={()=>categoryClick("Real-Estate")} text={"Real-Estate"}/>
                    <Radial active={active} onClick={()=>categoryClick("Automotive")} text={"Automotive"}/>
                    <Radial active={active} onClick={()=>categoryClick("Pet")} text={"Pet"}/>
                    <Radial active={active} onClick={()=>categoryClick("Newborn & Maternity")} text={"Newborn & Maternity"}/>
                    <Radial active={active} onClick={()=>categoryClick("Industrial")} text={"Industrial"}/>
                    <Radial active={active} onClick={()=>categoryClick("Still-Life")} text={"Still-Life"}/>
                    <Radial active={active} onClick={()=>categoryClick("Experimental")} text={"Experimental"}/>
                </div>
                <div className="
                
                    flex
                    flex-col
                    border-b-2
                    border-neutral-300
                    pb-5
                    w-[90vh]
                
                ">
                    <div className="
                    
                        pt-5
                        flex
                        flex-row
                        justify-center
                        items-center
                        gap-15
                    
                    ">   
                        <select defaultValue={""} name="" className="
                            border
                            border-neutral-400
                            rounded-xl
                            hover:shadow-[0_0_10px_rgba(0,0,0,0.25)]
                            focus:outline-none
                            focus:ring-0
                            focus:border-neutral-400    
                        ">
                            <option disabled value="">Order By</option>
                            <option value="Newest">Newest</option>
                            <option value="Oldest">Oldest</option>
                            <option value="Price">Price</option>
                            <option value="Rating">Rating</option>
                            <option value="none">none</option>
                        </select>
                        <select defaultValue={""} name="" className="
                            border
                            border-neutral-400
                            rounded-xl
                            hover:shadow-[0_0_10px_rgba(0,0,0,0.25)]
                            focus:outline-none
                            focus:ring-0
                            focus:border-neutral-400    
                        ">
                            <option disabled value="">Price Range</option>
                            <option value="10-50">10-50</option>
                            <option value="50-100">50-100</option>
                            <option value="100-150">100-150</option>
                            <option value="150-200">150-200</option>
                            <option value="200-250">200-250</option>
                            <option value="250-300">250-300</option>
                            <option value="350-400">350-400</option>
                            <option value="450-500">450-500</option>
                            <option value="550-600">550-600</option>
                            <option value="650-700+">650-700+</option>
                            
                        </select>
                        
                    </div>
                    <div className="
                    
                        pt-5
                        flex
                        flex-row
                        justify-center
                        items-center
                        gap-3
                    
                    ">   
                        <div className="
                        
                            flex
                            flex-row
                            justify-center
                            
                        ">
                            <label className="
                                
                                pr-3
                                
                            ">Distance Radius: {macCost} km</label>
                            <input type="range" min="1" max="1000" value={macCost} onChange={(e)=> setMaxCost(Number(e.target.value))} className="
                                
                                accent-neutral-500
                                
                            "/>
                        </div> 
                    </div>
                </div>
                <div className="
                
                    pt-5
                    flex
                    flex-col
                    justify-center
                    items-center
                    

                ">
                    <div className="
                    
                        w-[90vh]
                        flex
                        justify-between
                    
                    ">
                        <div className="
                        
                            flex
                            flex-row
                        
                        ">

                        
                            <select onChange={(e) => setActiveTag(e.target.value)} defaultValue={""} name="" className="
                                border
                                border-neutral-400
                                rounded-xl
                                hover:shadow-[0_0_10px_rgba(0,0,0,0.25)]
                                focus:outline-none
                                focus:ring-0
                                focus:border-neutral-400
                                mb-5    
                            ">
                                <option disabled value="">Tags</option>
                                <option value="Style">Style</option>
                                <option value="Lighting">Lighting</option>
                                <option value="Mood">Mood</option>
                                <option value="Setting">Setting</option>
                                
                            </select>
                            <p className="
                            
                                pl-5
                            
                            ">total Tags: {mood.length + lighting.length + style.length} selected</p>
                        </div>
                        <div>
                            <button onClick={clearTags} className="
                            
                                bg-red-600
                                text-white
                                rounded-xl
                                w-[10vh]
                                h-6
                                flex
                                items-center
                                justify-center
                                hover:bg-red-800
        
                                transition
                                duration-100

                                shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                                active:bg-white
                                active:text-black
                            
                            ">
                                
                            clear tags</button>
                        </div>

                    </div>
                    <div className={`


                        border-b-2
                        border-neutral-300
                        ${activeTag === "Style" ? "":"hidden"}
                    
                    `}>
                        <div className="
                        
                            w-[90vh]
                        
                        ">
                            <label className="
                            
                                font-light
                            
                            ">Style: {style.length} selected</label>
                        </div>
                        <div className="
                        
                            flex
                            flex-row
                            gap-1 
                            justify-center
                            pb-5
                        
                        ">
                            <Radial active={style} onClick={()=>styleClick("Cinematic")} text={"Cinematic"}/>
                            <Radial active={style} onClick={()=>styleClick("Minimalist")} text={"Minimalist"}/>
                            <Radial active={style} onClick={()=>styleClick("Vintage")} text={"Vintage"}/>
                            <Radial active={style} onClick={()=>styleClick("Dramatic")} text={"Dramatic"}/>
                            <Radial active={style} onClick={()=>styleClick("Stylized")} text={"Stylized"}/>
                            <Radial active={style} onClick={()=>styleClick("Contemporary")} text={"Contemporary"}/>
                        </div>
                    </div>
                    <div className={`
                    
                            border-b-2
                            border-neutral-300
                            ${activeTag === "Lighting" ? "":"hidden"}
                    
                    `}>
                        <div className="
                        
                            w-[90vh]
                            
                            pb-5
                        
                        ">
                            <label className="
                            
                                font-light
                            
                            ">Lighting: {lighting.length} selected</label>
                        </div>
                        <div className="
                        
                            flex
                            flex-row
                            gap-1 
                            h-15
                            shrink-0
                            overflow-y-auto
                            w-[90vh]
                            items-center
                        
                        ">
                            <Radial active={lighting} onClick={()=>lightingClick("Natural-Light")} text={"Natural-Light"}/>
                            <Radial active={lighting} onClick={()=>lightingClick("Golden-Hour")} text={"Golden-Hour"}/>
                            <Radial active={lighting} onClick={()=>lightingClick("Blue-Hour")} text={"Blue-Hour"}/>
                            <Radial active={lighting} onClick={()=>lightingClick("Low-Light")} text={"Low-Light"}/>
                            <Radial active={lighting} onClick={()=>lightingClick("High-Key")} text={"High-Key"}/>
                            <Radial active={lighting} onClick={()=>lightingClick("Low-Key")} text={"Low-Key"}/>
                            <Radial active={lighting} onClick={()=>lightingClick("Backlit")} text={"Backlit"}/>
                            <Radial active={lighting} onClick={()=>lightingClick("Flash")} text={"Flash"}/>
                            <Radial active={lighting} onClick={()=>lightingClick("Studio-Lighting")} text={"Studio-Lighting"}/>
                            <Radial active={lighting} onClick={()=>lightingClick("Moody")} text={"Moody"}/>
                        </div>
                    </div>
                    <div className={`
                    
                        border-b-2
                        border-neutral-300
                        ${activeTag === "Mood" ? "":"hidden"}
                    
                    `}>
                        <div className="
                        
                            w-[90vh]
                            
                            pb-5
                        
                        ">
                            <label className="
                            
                                font-light
                            
                            ">Mood: {mood.length} selected</label>
                        </div>
                        <div className="
                        
                            flex
                            flex-row
                            gap-1 
                            h-15
                            shrink-0
                            overflow-y-auto
                            w-[90vh]
                            items-center
                        
                        ">
                            <Radial active={mood} onClick={()=>moodClick("Joyful")} text={"Joyful"}/>
                            <Radial active={mood} onClick={()=>moodClick("Emotional")} text={"Emotional"}/>
                            <Radial active={mood} onClick={()=>moodClick("Energetic")} text={"Energetic"}/>
                            <Radial active={mood} onClick={()=>moodClick("Mysterious")} text={"Mysterious"}/>
                            <Radial active={mood} onClick={()=>moodClick("Peaceful")} text={"Peaceful"}/>
                            <Radial active={mood} onClick={()=>moodClick("Serious")} text={"Serious"}/>
                            <Radial active={mood} onClick={()=>moodClick("Intimate")} text={"Intimate"}/>
                            <Radial active={mood} onClick={()=>moodClick("Dramatic")} text={"Dramatic"}/>
                            <Radial active={mood} onClick={()=>moodClick("Playful")} text={"Playful"}/>
                        </div>
                    </div>
                    <p className="
                    
                        text-red-600
                    
                    ">Error message</p>
                    <div className="
                    
                        mt-10
                        flex
                        flex-row
                        gap-5
                    
                    ">
                        <button className="
                        
                            bg-black
                            w-[20vh]
                            h-10
                            rounded-md
                            text-white
                            mb-10

                            hover:bg-gray-900
                            mt-3

                            transition
                            duration-100

                            shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                            active:bg-white
                            active:text-black

                        ">continue</button>  
                        <button className="
                        
                            bg-black
                            w-[20vh]
                            h-10
                            rounded-md
                            text-white
                            mb-10

                            hover:bg-gray-900
                            mt-3

                            transition
                            duration-100

                            shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                            active:bg-white
                            active:text-black

                        ">Back</button>  
                    </div>
                </div>
            </div>
            
            <div className="
            
                flex
                border
                border-neutral-300
                w-5/10
                h-[80vh]
                shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                flex-col
                items-center
                rounded-md
                mt-10
                  
            ">
                <p className="
                
                    flex
                    flex-col
                    items-center
                    gap-3
                    mt-5
                    text-2xl
                
                ">Add to which album ?</p>
                <div className="
                    
                border
                border-neutral-300
                rounded-xl
                w-[70vh]
                h-[50vh]
                mt-5
                shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]
                flex
                flex-row
                pt-5
                gap-4
                overflow-y-auto
                justify-between
                shrink-0
                flex-wrap
                pl-5
                pr-5
                    
            ">
                <button className="
                    
                    bg-neutral-200
                    w-50
                    h-50
                    rounded-xl
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    pl-2
                    pt-2

                    hover:shadow-[0_0px_10px_rgba(0,0,0,0.5)]
                    transition
                    duration-100
                    flex
                    flex-col
                    items-start
                    active:shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]

                ">

                    <p className="
                        
                        text-2xl
                        
                    ">Title</p>
                    <p>100 photos</p> 
                </button>
                <button className="
                    
                    bg-neutral-200
                    w-50
                    h-50
                    rounded-xl
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    pl-2
                    pt-2

                    hover:shadow-[0_0px_10px_rgba(0,0,0,0.5)]
                    transition
                    duration-100
                    flex
                    flex-col
                    items-start
                    active:shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]

                ">

                    <p className="
                        
                        text-2xl
                        
                    ">Title</p>
                    <p>100 photos</p> 
                </button>
                <button className="
                    
                    bg-neutral-200
                    w-50
                    h-50
                    rounded-xl
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    pl-2
                    pt-2

                    hover:shadow-[0_0px_10px_rgba(0,0,0,0.5)]
                    transition
                    duration-100
                    flex
                    flex-col
                    items-start
                    active:shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]

                ">

                    <p className="
                        
                        text-2xl
                        
                    ">Title</p>
                    <p>100 photos</p> 
                </button>
                <button className="
                    
                    bg-neutral-200
                    w-50
                    h-50
                    rounded-xl
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    pl-2
                    pt-2

                    hover:shadow-[0_0px_10px_rgba(0,0,0,0.5)]
                    transition
                    duration-100
                    flex
                    flex-col
                    items-start
                    active:shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]

                ">

                    <p className="
                        
                        text-2xl
                        
                    ">Title</p>
                    <p>100 photos</p> 
                </button>
                <button className="
                    
                    bg-neutral-200
                    w-50
                    h-50
                    rounded-xl
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    pl-2
                    pt-2

                    hover:shadow-[0_0px_10px_rgba(0,0,0,0.5)]
                    transition
                    duration-100
                    flex
                    flex-col
                    items-start
                    active:shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]

                ">

                    <p className="
                        
                        text-2xl
                        
                    ">Title</p>
                    <p>100 photos</p> 
                </button>
                <button className="
                    
                    bg-neutral-200
                    w-50
                    h-50
                    rounded-xl
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    pl-2
                    pt-2

                    hover:shadow-[0_0px_10px_rgba(0,0,0,0.5)]
                    transition
                    duration-100
                    flex
                    flex-col
                    items-start
                    active:shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]

                ">

                    <p className="
                        
                        text-2xl
                        
                    ">Title</p>
                    <p>100 photos</p> 
                </button>
                <button className="
                    
                    bg-neutral-200
                    w-50
                    h-50
                    rounded-xl
                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                    pl-2
                    pt-2

                    hover:shadow-[0_0px_10px_rgba(0,0,0,0.5)]
                    transition
                    duration-100
                    flex
                    flex-col
                    items-start
                    active:shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]

                ">

                    <p className="
                        
                        text-2xl
                        
                    ">Title</p>
                    <p>100 photos</p> 
                </button>
            </div>   
            <p className="
            
                text-red-600
            
            ">Error message</p>
            <div className="
            
                w-[68vh]
                mt-5
                
            
            ">
                <Link  className="
                
                    text-neutral-600
                    hover:text-black
                    active:text-neutral-600
                
                ">Add album</Link>
            </div>
                <div className="
                
                        flex
                        flex-row
                        gap-10
                
                ">
                    <button className="
                
                        bg-black
                        w-[20vh]
                        h-10
                        rounded-md
                        text-white

                        hover:bg-gray-900
                        mt-10

                        transition
                        duration-100

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black

                        

                    
                    ">Submit</button>
                    <button className="
                
                        bg-black
                        w-[20vh]
                        h-10
                        rounded-md
                        text-white

                        hover:bg-gray-900
                        mt-10

                        transition
                        duration-100

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black

                        

                    
                    ">Back</button> 
                </div>
            </div>

        </div>
    )
    
}

export default AddImage