import { Link } from "react-router-dom"
import Radial from "../compnents/radial"
import { act, useState } from "react"

function SignUp() {

    const [active, setActive] = useState("");
    const[catagory, setCategory] = useState([])

    function clearCat(){

        setCategory([])

    }

    function radialClick(text){

        if(active === text){

            setActive("")

        }
        else{

            setActive(text)

        }

    }

    function categoryClick(text) {

        setCategory(prev => {
            if (prev.includes(text)) {
                return prev.filter(tag => tag !== text);
            } else {
                return [...prev, text];
            }
        });
    }

    return (
        <div
            className="
                w-full
                h-screen
                overflow-hidden
                bg-cover
                bg-center
                bg-no-repeat
                flex
                items-center
                justify-center
            "
            style={{
                backgroundImage: "url('/src/resources/ssscales (1).svg')"
            }}
        >

            <div className="
            
                flex
                flex-col
                bg-white
                rounded-md
                w-[40vh]
                h-[50vh]
                shadow-[0_0_10px_rgba(0,0,0,0.25)]
                items-center
                
            
            ">
                <div className="
                
                    flex
                    items-center
                    flex-col
                    hidden
                
                ">
                    <p className="
                    
                        text-4xl
                        font-bold
                        tracking-widest
                        pt-8
                    
                    ">Welcome</p>
                    <p className="
                    
                        text-2xl
                        tracking-wide
                        pt-8
                    
                    ">Enter Your Details</p>
                    <input placeholder="UserName" type="text" className="
                    
                        border-2
                        mt-10
                        h-10
                        rounded-md
                        w-[30vh]
                        pl-2
                        border-neutral-500
                        hover:border-black

                        transition
                        duration-100

                    "/>
                    <div className="
                    
                        flex
                        items-center
                        flex-col
                    
                    ">
                        <input placeholder="Password" type="password" className="
                        
                            border-2
                            mt-10
                            h-10
                            rounded-md
                            w-[30vh]
                            pl-2
                            border-neutral-500
                            
                            hover:border-black

                            transition
                            duration-100

                        
                        "/>
                        <input placeholder="Confirm Password" type="password" className="
                        
                            border-2
                            mt-2
                            h-10
                            rounded-md
                            w-[30vh]
                            pl-2
                            border-neutral-500
                            
                            hover:border-black

                            transition
                            duration-100


                        "/>
                        <p className="
                        
                            text-red-600
                        
                        ">Error message</p>
                    </div>
                    <button className="
                
                        bg-black
                        w-[30vh]
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
                </div>
                <div className="
                
                    flex 
                    flex-col
                    items-center
                    hidden
                
                ">
                    <p className="
                    
                        text-2xl
                        tracking-wide
                        pt-8
                    
                    ">Enter Your Details</p>
                    <input placeholder="Enter Email" type="email" className="
                            
                        border-2
                        mt-10
                        h-10
                        rounded-md
                        w-[30vh]
                        pl-2
                        border-neutral-500
                                
                        hover:border-black

                        transition
                        duration-100


                    "/>  
                    <p className="
                    
                        text-red-600
                    
                    ">Error Message</p>

                    <button  className="
                
                        bg-black
                        w-[30vh]
                        h-10
                        rounded-md
                        text-white

                        hover:bg-gray-900
                        mt-5

                        transition
                        duration-100

                        disabled:bg-neutral-500
                        disabled:active:text-white

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black

                    ">Submit</button>

                    <div className="
                    
                        flex
                        flex-col
                        items-center
                        pt-5
                        

                    ">
                        <p>Enter the code sent to your email</p>
                        <input placeholder="X X X X X" type="text" className="
                                
                            border-2
                            mt-3
                            h-10
                            rounded-md
                            w-[30vh]
                               
                            border-neutral-500
                                        
                            hover:border-black

                            transition
                            duration-100
                            text-center
                        "/>  
                        <button className="
                        
                            text-neutral-500
                            pt-2

                            hover:text-neutral-600
                            active:text-black

                            disabled:hover:text-neutral-500
                            disabled:active:text-neutral-500
                        
                        ">
                        Resend Email
                        </button>
                        <p className="
                        
                            text-red-600
                        
                        ">error message</p>
                    <button className="
                
                        bg-black
                        w-[30vh]
                        h-10
                        rounded-md
                        text-white

                        hover:bg-gray-900
                        mt-5

                        transition
                        duration-100

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black

                    ">continue</button>

                    </div>
                </div>
                <div className="
                
                    flex
                    flex-col
                    items-center
                    hidden
                
                ">

                    <p className="
                    
                        text-2xl
                        tracking-wide
                        pt-8
                    
                    ">What are you looking for ?</p>
                    <div className="
                    
                        flex
                        flex-row
                        items-center
                        gap-3
                        pt-15
                    ">
                        <Radial onClick={() => {radialClick("Photographers")}} text="Photographers" active={active}/>
                        <Radial onClick={() => {radialClick("Potential Clients")}} text="Potential Clients" active={active}/>
                    </div>

                    <button className="
                
                        bg-black
                        w-[30vh]
                        h-10
                        rounded-md
                        text-white

                        hover:bg-gray-900
                        mt-15

                        transition
                        duration-100

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black

                    ">continue</button>

                </div>
                <div className="

                    flex
                    flex-col
                    items-center
                    hidden 
                    
                
                ">
                    <p className="
                        
                        text-2xl
                        tracking-wide
                        pt-8
                        
                    ">Where are you located ?</p>   
                    <div className="
                    
                        flex
                        flex-col
                        items-center
                        gap-3
                        mt-5
                    
                    ">
                        <input placeholder="Adress line 1" type="text" className="
                                
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
                        <input placeholder="Adress line 2" type="text" className="
                                
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
                        <input placeholder="Town/City" type="text" className="
                                
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
                        <input placeholder="PostCode" type="text" className="
                                
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
                        <input placeholder="Country" type="text" className="
                                
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
                    <p className="
                    
                        text-red-600
                    
                    ">Error message</p>
                    <button className="
                
                        bg-black
                        w-[30vh]
                        h-10
                        rounded-md
                        text-white

                        hover:bg-gray-900
                        mt-15

                        transition
                        duration-100

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black

                    ">continue</button>                                                     
                </div>  
                <div className="
                
                    flex
                    flex-col
                    items-center
                    hidden
                
                ">
                    <p className="
                        
                        text-2xl
                        tracking-wide
                        pt-8
                        
                    ">type of photography ?</p>                 
                        <div className="

                            mt-8
                            border-b-2
                            w-[35vh]
                            border-neutral-300
                            h-[25vh]
                            flex
                            gap-1
                            shrink-0
                            flex-wrap
                            overflow-y-auto
                            justify-center
                            shadow-[0_0_10px_rgba(0,0,0,0.25)_inset]
                            rounded-xl
                            p-2

                        
                        ">  
                            <Radial active={catagory} onClick={()=>categoryClick("Wedding")} text={"Wedding"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Street")} text={"Street"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Portrait")} text={"Portrait"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Event")} text={"Event"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Fine Art")} text={"Fine Art"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Landscape")} text={"Landscape"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Wild Life")} text={"Wild Life"}/>  
                            <Radial active={catagory} onClick={()=>categoryClick("Fashion")} text={"Fashion"}/>  
                            <Radial active={catagory} onClick={()=>categoryClick("Comercial")} text={"Comercial"}/>  
                            <Radial active={catagory} onClick={()=>categoryClick("Architectural")} text={"Architectural"}/>  
                            <Radial active={catagory} onClick={()=>categoryClick("Food")} text={"Food"}/>  
                            <Radial active={catagory} onClick={()=>categoryClick("Product")} text={"Product"}/> 
                            <Radial active={catagory} onClick={()=>categoryClick("Sports")} text={"Sports"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Documentary")} text={"Documentary"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Photo Journalism")} text={"Photo Journalism"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Macro")} text={"Macro"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Astrophotography")} text={"Astrophotography"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Aerial")} text={"Aerial"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Underwater")} text={"Underwater"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Night")} text={"Night"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Long-Exposure")} text={"Long-Exposure"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Black & White")} text={"Black & White"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Scientific")} text={"Scientific"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Medical")} text={"Medical"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Real-Estate")} text={"Real-Estate"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Automotive")} text={"Automotive"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Pet")} text={"Pet"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Newborn & Maternity")} text={"Newborn & Maternity"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Industrial")} text={"Industrial"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Still-Life")} text={"Still-Life"}/>
                            <Radial active={catagory} onClick={()=>categoryClick("Experimental")} text={"Experimental"}/>
                        </div>
                        <div className="
                        
                            flex
                            flex-row
                            justify-between
                            w-[30vh]
                        
                        ">
                            <p>{catagory.length} selected</p>
                            <button onClick={clearCat} className="
                            
                                text-neutral-600
                                hover:text-black
                                active:text-neutral-600
                            
                            ">clear all</button>
                        </div>
                        <p className="
                        
                            text-red-600
                        
                        ">Error message</p>
                        <button className="
                    
                            bg-black
                            w-[30vh]
                            h-10
                            rounded-md
                            text-white

                            hover:bg-gray-900
                            mt-3

                            transition
                            duration-100

                            shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                            active:bg-white
                            active:text-black

                        ">continue</button>  
                </div>
                <div className="
                
                    flex
                    flex-col
                    items-center
                
                ">
                    <p className="
                        
                        text-2xl
                        tracking-wide
                        pt-8
                        
                    ">Enter your price details</p>  
                    <div className="

                        mt-5
                        h-[30vh]
                        rounded-xl
                        p-2
                        shadow-[0_0_10px_rgba(0,0,0,0.25)_inset]
                        w-[32vh]
                        flex
                        flex-col
                        items-center
                        shrink-0
                        overflow-y-auto
                        gap-2
                    
                    ">
                        <div className="
                        
                            flex
                            flex-col
                        
                        ">
                            
                            <p className="
                            
                                text-[1.2vh]
                                text-neutral-600
                            
                            ">category: example</p>
                            <div className="
                            
                                flex
                                flex-row
                                w-[27vh]
                                justify-between
                                
                            
                            ">
                                
                                <p>Price per 10 pictures</p>
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
                        </div>     
                        <div className="
                        
                            flex
                            flex-col
                        
                        ">
                            
                            <p className="
                            
                                text-[1.2vh]
                                text-neutral-600
                            
                            ">category: example</p>
                            <div className="
                            
                                flex
                                flex-row
                                w-[27vh]
                                justify-between
                                
                            
                            ">
                                
                                <p>Price per 10 pictures</p>
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
                        </div>     
                        <div className="
                        
                            flex
                            flex-col
                        
                        ">
                            
                            <p className="
                            
                                text-[1.2vh]
                                text-neutral-600
                            
                            ">category: example</p>
                            <div className="
                            
                                flex
                                flex-row
                                w-[27vh]
                                justify-between
                                
                            
                            ">
                                
                                <p>Price per 10 pictures</p>
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
                        </div>     
                        <div className="
                        
                            flex
                            flex-col
                        
                        ">
                            
                            <p className="
                            
                                text-[1.2vh]
                                text-neutral-600
                            
                            ">category: example</p>
                            <div className="
                            
                                flex
                                flex-row
                                w-[27vh]
                                justify-between

                            
                            ">
                                
                                <p>Price per 10 pictures</p>
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
                        </div>     
                        <div className="
                        
                            flex
                            flex-col
                        
                        ">
                            
                            <p className="
                            
                                text-[1.2vh]
                                text-neutral-600
                            
                            ">category: example</p>
                            <div className="
                            
                                flex
                                flex-row
                                w-[27vh]
                                justify-between
                                
                            
                            ">
                                
                                <p>Price per 10 pictures</p>
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
                        </div>     
                        <div className="
                        
                            flex
                            flex-col
                        
                        ">
                            
                            <p className="
                            
                                text-[1.2vh]
                                text-neutral-600
                            
                            ">category: example</p>
                            <div className="
                            
                                flex
                                flex-row
                                w-[27vh]
                                justify-between
                                
                            
                            ">
                                
                                <p>Price per 10 pictures</p>
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
                        </div>     
                    </div>
                    <p className="
                    
                        text-red-600 
                    
                    ">Error message</p>
                    <button className="
                    
                        bg-black
                        w-[30vh]
                        h-10
                        rounded-md
                        text-white

                        hover:bg-gray-900
                        mt-3

                        transition
                        duration-100

                        shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                        active:bg-white
                        active:text-black

                    ">continue</button> 
                </div>
            </div>


        </div>
    )
}

export default SignUp