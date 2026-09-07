
    import CHeader from "../compnents/cheader"
    import { useState, useRef, useEffect, use } from "react";
    import { useNavigate } from "react-router-dom";
    import { jwtService } from "../logic/jwt";
    import { Link } from "react-router-dom";
    import Radial from "../compnents/radial";

    function CreateProfile(){
        
        const [errorMessage, setErrorMessage] = useState("")
        const title = useRef();
        const [saving, setSaving] = useState(false)
        const description = useRef();
        const [active, setActive] = useState("None selected")
        const [activePage, setActivePage] = useState("details")
        const [minCost, setMinCost] = useState(0)
        const [maxCost, setMaxCost] = useState(0)
        const addressLine1 = useRef()
        const addressLine2= useRef()
        const townCity = useRef()
        const postCode = useRef()
        const country = useRef()
        const [images, setImages] = useState(0)
        const [currentImage, setCurrentImage] = useState()
        const [imageCounter, setImageCounter] = useState(0)
        const [likes, setLikes] = useState([])
        const [dislikes, setDislikes] = useState([])
        const [neutral, setNeutral] = useState([])
        const [coord, setCoord] = useState([])

        

        const navigate = useNavigate()

        const tokenService = new jwtService()

        useEffect(() =>{
            console.log("ran")
            fetch("/api/reco/client/getpreferencephotos", {

                method:"POST",
                headers:{

                    "Content-Type":"application/json",
                    auth:localStorage.getItem("jwt")

                },
                body:JSON.stringify({

                    amount:20

                })

            }).then(response => response.json()).then(async data => {

                if(!data.sucsess){

                    if(data.internalCode === 401){
                        let result = await tokenService.requestJwt()
                        if(result === true){

                            fetch("/api/reco/client/getpreferencephotos", {

                                method:"POST",
                                headers:{

                                    "Content-Type":"application/json",
                                    auth:localStorage.getItem("jwt")

                                },
                                body:JSON.stringify({

                                    amount:20

                                })

                            }).then(res => res.json()).then(d => {

                                if(!d.sucsess){

                                    //ERROR 

                                }
                                else{

                                    setImages(d.data)
                                    setCurrentImage(d.data[imageCounter])
                                    console.log(d.data)

                                }

                            })                 

                        }
                    }
                    else{

                        //ERROR

                    }

                }
                else{

                    setImages(data.data)
                    setCurrentImage(data.data[imageCounter])
                    console.log(data.data)

                }

            })
            
        }, [])

        useEffect(() => {

            setCurrentImage(images[imageCounter])

        }, [imageCounter])

        function imageReaction(reaction) {

            if (imageCounter !== images.length - 1) {

                if (reaction === "like") {

                    setLikes([...likes, currentImage.photoid]);
                    setImageCounter(imageCounter + 1);

                }
                else if (reaction === "neutral") {

                    setNeutral([...neutral, currentImage.photoid]);
                    setImageCounter(imageCounter + 1);

                }
                else if (reaction === "dislike") {

                    setDislikes([...dislikes, currentImage.photoid]);
                    setImageCounter(imageCounter + 1);

                }
            }else{

                fetch("/api/profile/client/addprofile",{

                    method:"POST",
                    headers:{

                        "Content-Type":"application/json",
                        auth:localStorage.getItem("jwt")

                    },
                    body:JSON.stringify({

                        profilename:title.current.value,
                        profiledescription:description.current.value,
                        mincost:minCost,
                        maxcost:maxCost,
                        projectcatagory:active,
                        latitude:coord[0],
                        longitude:coord[1],
                        likes:likes,
                        dislikes:dislikes

                    })

                }).then(response => response.json()).then(async data => {
                    console.log(data)
                    if(!data.sucsess){

                        if(data.internalCode === 401){

                            result = await tokenService.requestJwt()
                            if(result){

                                fetch("/api/profile/client/addprofile",{

                                    method:"POST",
                                    headers:{

                                        "Content-Type":"application/json",
                                        auth:localStorage.getItem("jwt")

                                    },
                                    body:JSON.stringify({

                                        profilename:title.current.value,
                                        profiledescription:description.current.value,
                                        mincost:minCost,
                                        maxcost:maxCost,
                                        projectcatagory:active,
                                        latitude:coord[0],
                                        longitude:coord[1],
                                        likes:likes,
                                        dislikes:dislikes

                                    })

                                }).then(res => res.json()).then(d => {

                                    if(!d.sucsess){

                                        //error

                                    }else{

                                        navigate("/profiles")

                                    }
                                    
                                })

                            }

                        }else{

                            //error
                            
                        }

                    }else{

                        navigate("/profiles")

                    }

                })   

            }
        }
    
        function submitAddress(){

            let addressString = addressLine1.current.value + ", " + addressLine2.current.value + ", " + townCity.current.value + ", " + postCode.current.value + ", " + country.current.value

            fetch("/api/geo/getcoord", {

                method:"POST",
                headers:{

                    "Content-Type": "application/json",
                    auth:localStorage.getItem("jwt")

                },
                body:JSON.stringify({

                    address:addressString

                })

            }).then(response => response.json()).then(async data => {

                

                if(!data.sucsess){

                    if(data.internalCode === 401){


                        let result = await tokenService.requestJwt()


                        if(result === true){

                            fetch("/api/geo/getcoord", {

                                method:"POST",
                                headers:{

                                    "Content-Type": "application/json",
                                    auth:localStorage.getItem("jwt")

                                },
                                body:JSON.stringify({

                                    address:addressString

                                })

                            }).then(res => res.json()).then(d => {

                                if(d.sucsess){

                                    setCoord(d.data)
                                    setActivePage("preference")

                                }
                                else{

                                    setErrorMessage(d.error)

                                }

                            })                          
                            
                        }

                    }
                    else{

                        setErrorMessage(data.error)

                    }

                }
                else{

                    setCoord(data.data)
                    setActivePage("preference")

                }

            })

        }

        function categoryClick(text){

            if(text === active){

                setActive("None selected")

            }
            else{
                setActive(text)
            }

        }

        function back(){


            if(activePage === "address"){

                setActivePage("details")

            }

        }

        function submitDetails(){

            if(title.current.value === ""){

                setErrorMessage("Must enter a title")

            }
            else if(description.current.value === ""){

                setErrorMessage("must enter a description")

            }
            else if(active === "None selected"){

                setErrorMessage("must select a category")

            }
            else if(minCost > maxCost && (!maxCost === 0)){

                setErrorMessage("Min Cost cannot be more than Max Cost")

            }
            else if(minCost === maxCost && (!maxCost === 0)){
                
                setErrorMessage("Min Cost cannot be equal to MaxCost")
                
            }
            else{

                setErrorMessage("")
                setActivePage("address")

            }

        }

        return(
            <div className="
            
                w-screen 
                min-h-screen 
            
            ">
                <CHeader active="Search"/>
                <div className={`
                
                    w-full
                    h-full
                    flex
                    justify-center
                    mt-20
                    ${activePage === "details"? "":"hidden"}
                
                `}>
                    <div className="
                    
                        border-2
                        border-neutral-300
                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                        w-[100vh]
                        h-[75vh]
                        rounded-xl
                        flex
                        flex-col
                        items-center

                    
                    ">
        
                        <div className="
                        
                            flex
                            flex-row
                            w-[80vh]
                            justify-between
                            mt-10
                        
                        ">
                            <input ref={title} placeholder="Title" type="text" className="
                            
                                border-2
                        
                                h-10
                                rounded-md
                                w-6/10
                                pl-2
                                border-neutral-500
                                hover:border-black

                                transition
                                duration-200
                            

                            
                            "/>
                            <div className="
                            
                                w-3/10
                                border-2
                        
                        
                                rounded-md
                                pl-2
                                border-neutral-500

                            
                            ">

                                

                            </div>
                        </div>
                        <textarea ref={description} placeholder="Desctiption" className="
                        
                            w-[80vh]
                            border-2
                            rounded-md
                            border-neutral-500
                            hover:border-black
                            transition
                            duration-200
                            mt-5
                            h-[30vh]
                            pl-2
                            resize-none

                        
                        "/>

                        <div>
                            
                        <div className="
                        
                            w-[80vh]
                            mt-5

                        ">
                            <p className="
                            
                                font-light
                            
                            ">Category: {active}</p>
                        </div>
                            <div className="
                            
                                border-b-2
                                w-[80vh]
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
                        </div>
                        <div className="
                        
                            pt-5
                            flex
                            flex-col    
                            justify-center
                            items-center
                            gap-3
                            border-b-2
                            border-neutral-300
                            
                            w-[80vh]
                        
                        ">   
                            <p className="
                            
                                text-center
                            
                            
                            ">Cost per 10 photos</p>
                            <div className="
                            
                                flex
                                flex-row
                                gap-10
                            
                            ">
                                <div className="
                                
                                    flex
                                    flex-row
                                    items-center
                                
                                ">
                                    <label>Min Cost: {minCost === 1000 ? `$${minCost}+` : minCost === 0 ? "none" : `$${minCost}`}</label>
                                    <input value={minCost} type="range" min={0} max={1000} onChange={(e) => setMinCost(Number(e.target.value))}/>
                                </div>
                                
                                <div className="
                                
                                    flex
                                    flex-row
                                    items-center                        
                                
                                ">
                                    <label>Max Cost: {maxCost === 1000 ? `$${maxCost}+` : maxCost === 0 ? "none" : `$${maxCost}`}</label>
                                    <input value={maxCost} type="range" min={0} max={1000} onChange={(e) => setMaxCost(Number(e.target.value))}/>
                                </div>
                            </div>
                        </div>
                        <div className="
                        
                            mt-5
                            flex
                            flex-col
                            items-center   
                        
                        ">
                            <p className="
                            
                                text-red-600
                                absolute
                                
                            
                            ">{errorMessage}</p>
                            <div className={`
                            
                                flex
                                flex-row
                                w-[80vh]
                                items-center
                                justify-center
                                gap-10
                                mt-7
                                pt-4
                            
                            `}>
                                <button onClick={submitDetails} className="
                                
                                    bg-neutral-700
                                    text-white
                                    rounded-xl
                                    w-[10vh]
                                    h-8.75
                                    flex
                                    items-center
                                    justify-center
                                    hover:bg-gray-900
            
                                    transition
                                    duration-100

                                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                                    active:bg-white
                                    active:text-black

                                
                                ">Submit</button>
                                <Link to="/profiles" className="
                                
                                    bg-red-500
                                    text-white
                                    rounded-xl
                                    w-[10vh]
                                    h-8.75
                                    flex
                                    items-center
                                    justify-center
                                    hover:bg-red-700
            
                                    transition
                                    duration-100

                                    shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                                    active:bg-white
                                    active:text-black
                                
                                ">Back</Link>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="
                
                    w-full
                    h-full
                    flex
                    justify-center
                    mt-20
                
                ">
                    <div className={`
                    
                        border-2
                        border-neutral-300
                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                        w-[60vh]
                        h-[70vh]
                        rounded-xl
                        flex
                        flex-col
                        items-center
                        ${activePage === "address"? "":"hidden"}

                    
                    `}>
                        <p className="

                            mt-10
                            text-2xl
                        
                        ">Enter base location of profile</p>
                        <div className="
                        
                            flex
                            flex-col
                            items-center
                            gap-7
                            mt-10
                        
                        ">
                            <input ref={addressLine1} placeholder="Adress line 1" type="text" className="
                                    
                                border-2
                        
                                h-10
                                rounded-md
                                w-[30vh]
                                pl-2
                                border-neutral-500
                                        
                                hover:border-black

                                transition
                                duration-100


                            "/>       
                            <input ref={addressLine2} placeholder="Adress line 2" type="text" className="
                                    
                                border-2
                            
                                h-10
                                rounded-md
                                w-[30vh]
                                pl-2
                                border-neutral-500
                                        
                                hover:border-black

                                transition
                                duration-100


                            "/>    
                            <input ref={townCity} placeholder="Town/City" type="text" className="
                                    
                                border-2
                            
                                h-10
                                rounded-md
                                w-[30vh]
                                pl-2
                                border-neutral-500
                                        
                                hover:border-black

                                transition
                                duration-100


                            "/>   
                            <input ref={postCode} placeholder="PostCode" type="text" className="
                                    
                                border-2
                            
                                h-10
                                rounded-md
                                w-[30vh]
                                pl-2
                                border-neutral-500
                                        
                                hover:border-black

                                transition
                                duration-100


                            "/>   
                            <input ref={country} placeholder="Country" type="text" className="
                                    
                                border-2
                    
                                h-10
                                rounded-md
                                w-[30vh]
                                pl-2
                                border-neutral-500
                                        
                                hover:border-black

                                transition
                                duration-100


                            "/>   
                        </div> 
                        <button className="
                        
                                text-neutral-500
                                hover:text-black
                                active:text-neutral-500
                                pt-5
                        
                        ">Use User's base location</button>
                        <p className="
                        
                                text-red-600
                                
                        ">Error Message</p>
                        <div className="

                                mt-10
                                flex
                                gap-5
                        
                        ">
                            <button onClick={submitAddress} className="
                                
                                bg-neutral-700
                                text-white
                                rounded-xl
                                w-[15vh]
                                h-8.75
                                flex
                                items-center
                                justify-center
                                hover:bg-gray-900
            
                                transition
                                duration-100

                                shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                                active:bg-white
                                active:text-black

                                
                            ">Submit</button>
                            <button onClick={back} className="
                                
                                bg-red-500
                                text-white
                                rounded-xl
                                w-[15vh]
                                h-8.75
                                flex
                                items-center
                                justify-center
                                hover:bg-red-700
            
                                transition
                                duration-100

                                shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                                active:bg-white
                                active:text-black
                                
                            ">Back</button>
                        </div>
                    </div>
                    <div className={`
                    
                        border-2
                        border-neutral-300
                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                        w-[100vh]
                        h-[75vh]
                        rounded-xl
                        flex
                        flex-col
                        items-center
                        ${activePage === "preference"? "": "hidden"}
                    
                    `}>
                        <p className="
                        
                            text-2xl
                            mt-8
                        
                        ">How much do you like this photo ?</p>
                        <img src={currentImage? "/api" + currentImage.thumbnailurl: ""} className="

                            mt-5
                            shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                            rounded-2xl
                            
                            h-[50vh]
                        
                        "/>
                        <div className="
                        
                            flex
                            flex-row
                            gap-5
                            pt-10
                        
                        ">
                            <button onClick={() => {imageReaction("like")}} className="
                            
                                bg-green-500
                                rounded-4xl
                                p-1
                                w-[5vh]
                                shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                                hover:bg-green-600
                                active:bg-green-500
                            
                            ">
                                <img className="

                                
                                " src="src\resources\Mood-Happy--Streamline-Sharp-Streamline-Material.png"/>
                            </button>
                            <button onClick={() => {imageReaction("neutral")}} className="
                            
                                bg-orange-500
                                rounded-4xl
                                p-1
                                w-[5vh]
                                shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                                hover:bg-orange-600
                                active:bg-orange-500
                            
                            ">
                                <img src="src\resources\Sentiment-Neutral--Streamline-Sharp-Streamline-Material.png"/>
                            </button>
                            <button onClick={() => {imageReaction("dislike")}} className="
                            
                                bg-red-500
                                rounded-4xl
                                p-1
                                w-[5vh]
                                shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                                hover:bg-red-600
                                active:bg-red-500
                            
                            ">
                                <img src="src\resources\Sentiment-Dissatisfied--Streamline-Sharp-Streamline-Material.png"/>
                            </button>
                        </div>
                    </div>

                </div>  
                            
            </div>
        )

    }

    export default CreateProfile